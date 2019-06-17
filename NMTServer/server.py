from __future__ import absolute_import, division, print_function, unicode_literals
from flask import Flask
from flask import request

import tensorflow as tf

import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split

import unicodedata
import re
import numpy as np
import os
import io
import time
import jieba


path_to_file = "cmn.txt"
BATCH_SIZE = 64
embedding_dim = 256
units = 1024
num_examples = 30000


# Converts the unicode file to ascii
def unicode_to_ascii(s):
	return ''.join(c for c in unicodedata.normalize('NFD', s)
		if unicodedata.category(c) != 'Mn')


def preprocess_sentence(w):
	w = unicode_to_ascii(w.lower().strip())

	# creating a space between a word and the punctuation following it
	# eg: "he is a boy." => "he is a boy ."
	# Reference:- https://stackoverflow.com/questions/3645931/python-padding-punctuation-with-white-spaces-keeping-punctuation
	w = re.sub(r"([?.!,¿])", r" \1 ", w)
	w = re.sub(r'[" "]+', " ", w)

	# replacing everything with space except (a-z, A-Z, ".", "?", "!", ",")
	w = re.sub(r"[^a-zA-Z?.!,¿]+", " ", w)

	w = w.rstrip().strip()

	# adding a start and an end token to the sentence
	# so that the model know when to start and stop predicting.
	w = '<start> ' + w + ' <end>'
	return w

def preprocess_sentence_zh(w):
	seg_list = jieba.cut(w.strip(), cut_all=False)
	w = '<start> ' + " ".join(seg_list) + ' <end>'
	return w


# 1. Remove the accents
# 2. Clean the sentences
# 3. Return word pairs in the format: [ENGLISH, SPANISH]
def create_dataset(path, num_examples):
	lines = io.open(path, encoding='UTF-8').read().strip().split('\n')

	word_pairs = [[preprocess_sentence(l.split('\t')[0]), preprocess_sentence_zh(l.split('\t')[1])]	for l in lines[:num_examples]]

	return zip(*word_pairs)


def max_length(tensor):
	return max(len(t) for t in tensor)


def tokenize(lang):
	lang_tokenizer = tf.keras.preprocessing.text.Tokenizer(
		filters='')
	lang_tokenizer.fit_on_texts(lang)

	tensor = lang_tokenizer.texts_to_sequences(lang)

	tensor = tf.keras.preprocessing.sequence.pad_sequences(tensor,
														 padding='post')

	return tensor, lang_tokenizer


def load_dataset(path, num_examples=None):
	# creating cleaned input, output pairs
	# targ_lang, inp_lang = create_dataset(path, num_examples)
	inp_lang, targ_lang = create_dataset(path, num_examples)

	input_tensor, inp_lang_tokenizer = tokenize(inp_lang)
	target_tensor, targ_lang_tokenizer = tokenize(targ_lang)

	return input_tensor, target_tensor, inp_lang_tokenizer, targ_lang_tokenizer


def convert(lang, tensor):
	for t in tensor:
		if t!=0:
			print ("%d ----> %s" % (t, lang.index_word[t]))

class Encoder(tf.keras.Model):
	def __init__(self, vocab_size, embedding_dim, enc_units, batch_sz):
		super(Encoder, self).__init__()
		self.batch_sz = batch_sz
		self.enc_units = enc_units
		self.embedding = tf.keras.layers.Embedding(vocab_size, embedding_dim)
		self.gru = tf.keras.layers.GRU(self.enc_units,
										 return_sequences=True,
										 return_state=True,
										 recurrent_initializer='glorot_uniform')

	def call(self, x, hidden):
		x = self.embedding(x)
		output, state = self.gru(x, initial_state = hidden)
		return output, state

	def initialize_hidden_state(self):
		return tf.zeros((self.batch_sz, self.enc_units))



class BahdanauAttention(tf.keras.Model):
	def __init__(self, units):
		super(BahdanauAttention, self).__init__()
		self.W1 = tf.keras.layers.Dense(units)
		self.W2 = tf.keras.layers.Dense(units)
		self.V = tf.keras.layers.Dense(1)

	def call(self, query, values):
		# hidden shape == (batch_size, hidden size)
		# hidden_with_time_axis shape == (batch_size, 1, hidden size)
		# we are doing this to perform addition to calculate the score
		hidden_with_time_axis = tf.expand_dims(query, 1)

		# score shape == (batch_size, max_length, hidden_size)
		score = self.V(tf.nn.tanh(
				self.W1(values) + self.W2(hidden_with_time_axis)))

		# attention_weights shape == (batch_size, max_length, 1)
		# we get 1 at the last axis because we are applying score to self.V
		attention_weights = tf.nn.softmax(score, axis=1)

		# context_vector shape after sum == (batch_size, hidden_size)
		context_vector = attention_weights * values
		context_vector = tf.reduce_sum(context_vector, axis=1)

		return context_vector, attention_weights




class Decoder(tf.keras.Model):
	def __init__(self, vocab_size, embedding_dim, dec_units, batch_sz):
		super(Decoder, self).__init__()
		self.batch_sz = batch_sz
		self.dec_units = dec_units
		self.embedding = tf.keras.layers.Embedding(vocab_size, embedding_dim)
		self.gru = tf.keras.layers.GRU(self.dec_units,
																	 return_sequences=True,
																	 return_state=True,
																	 recurrent_initializer='glorot_uniform')
		self.fc = tf.keras.layers.Dense(vocab_size)

		# used for attention
		self.attention = BahdanauAttention(self.dec_units)

	def call(self, x, hidden, enc_output):
		# enc_output shape == (batch_size, max_length, hidden_size)
		context_vector, attention_weights = self.attention(hidden, enc_output)

		# x shape after passing through embedding == (batch_size, 1, embedding_dim)
		x = self.embedding(x)

		# x shape after concatenation == (batch_size, 1, embedding_dim + hidden_size)
		x = tf.concat([tf.expand_dims(context_vector, 1), x], axis=-1)

		# passing the concatenated vector to the GRU
		output, state = self.gru(x)

		# output shape == (batch_size * 1, hidden_size)
		output = tf.reshape(output, (-1, output.shape[2]))

		# output shape == (batch_size, vocab)
		x = self.fc(output)

		return x, state, attention_weights


def loss_function(real, pred):
	mask = tf.math.logical_not(tf.math.equal(real, 0))
	loss_ = loss_object(real, pred)

	mask = tf.cast(mask, dtype=loss_.dtype)
	loss_ *= mask

	return tf.reduce_mean(loss_)

@tf.function
def train_step(inp, targ, enc_hidden):
	loss = 0
  
	with tf.GradientTape() as tape:
		enc_output, enc_hidden = encoder(inp, enc_hidden)
	  
		dec_hidden = enc_hidden
	  
		dec_input = tf.expand_dims([targ_lang.word_index['<start>']] * BATCH_SIZE, 1)
	  
		# Teacher forcing - feeding the target as the next input
		for t in range(1, targ.shape[1]):
		  # passing enc_output to the decoder
		  predictions, dec_hidden, _ = decoder(dec_input, dec_hidden, enc_output)
	  
		  loss += loss_function(targ[:, t], predictions)
	  
		  # using teacher forcing
		  dec_input = tf.expand_dims(targ[:, t], 1)
  
	batch_loss = (loss / int(targ.shape[1]))
  
	variables = encoder.trainable_variables + decoder.trainable_variables
  
	gradients = tape.gradient(loss, variables)
  
	optimizer.apply_gradients(zip(gradients, variables))
  
	return batch_loss

def train(EPOCHS = 2):

	for epoch in range(EPOCHS):
		start = time.time()

		enc_hidden = encoder.initialize_hidden_state()
		total_loss = 0

		for (batch, (inp, targ)) in enumerate(dataset.take(steps_per_epoch)):
			batch_loss = train_step(inp, targ, enc_hidden)
			total_loss += batch_loss

		if batch % 100 == 0:
			print('Epoch {} Batch {} Loss {:.4f}'.format(epoch + 1,
														 batch,
														 batch_loss.numpy()))
		# saving (checkpoint) the model every 2 epochs
		if (epoch + 1) % 2 == 0:
			checkpoint.save(file_prefix = checkpoint_prefix)

		print('Epoch {} Loss {:.4f}'.format(epoch + 1,
										  total_loss / steps_per_epoch))
		print('Time taken for 1 epoch {} sec\n'.format(time.time() - start))


def creat_model(vocab_inp_size, vocab_tar_size, dataset):
	example_input_batch, example_target_batch = next(iter(dataset))
	example_input_batch.shape, example_target_batch.shape

	encoder = Encoder(vocab_inp_size, embedding_dim, units, BATCH_SIZE)
	attention_layer = BahdanauAttention(10)
	decoder = Decoder(vocab_tar_size, embedding_dim, units, BATCH_SIZE)


	optimizer = tf.keras.optimizers.Adam()
	loss_object = tf.keras.losses.SparseCategoricalCrossentropy(
			from_logits=True, reduction='none')

	return encoder, decoder, optimizer, loss_object

def translate(sentence):
	s = preprocess_sentence(sentence).split(' ')
	sentence = []
	for token in s[1:-1]:
		try:
			inp_lang.word_index[token]
			sentence.append(token)
		except Exception as e:
			sentence.append('?')
	print("".join(sentence))
	result, sentence, attention_plot = evaluate("".join(sentence))

	print('Input: %s' % (sentence))
	print('Predicted translation: {}'.format(result))



# =================================== Chinese To English ======================================= 


def evaluate_zh2en(sentence):
	attention_plot = np.zeros((max_length_targ_zh2en, max_length_inp_zh2en))
	sentence = preprocess_sentence_zh(sentence)
	inputs = [inp_lang_zh2en.word_index[i] for i in sentence.split(' ')]
	inputs = tf.keras.preprocessing.sequence.pad_sequences([inputs],
														   maxlen=max_length_inp_zh2en,
														   padding='post')
	inputs = tf.convert_to_tensor(inputs)

	result = ''

	hidden = [tf.zeros((1, units))]
	enc_out, enc_hidden = encoder_zh2en(inputs, hidden)

	dec_hidden = enc_hidden
	dec_input = tf.expand_dims([targ_lang_zh2en.word_index['<start>']], 0)

	for t in range(max_length_targ_zh2en):
		predictions, dec_hidden, attention_weights = decoder_zh2en(dec_input,
															 dec_hidden,
															 enc_out)

		# storing the attention weights to plot later on
		attention_weights = tf.reshape(attention_weights, (-1, ))
		attention_plot[t] = attention_weights.numpy()

		predicted_id = tf.argmax(predictions[0]).numpy()

		result += targ_lang_zh2en.index_word[predicted_id] + ' '

		if targ_lang_zh2en.index_word[predicted_id] == '<end>':
			return result, sentence, attention_plot

		# the predicted ID is fed back into the model
		dec_input = tf.expand_dims([predicted_id], 0)

	return result, sentence, attention_plot

# Try experimenting with the size of that dataset
target_tensor_zh2en, input_tensor_zh2en, targ_lang_zh2en, inp_lang_zh2en = load_dataset(path_to_file, num_examples)

# Calculate max_length of the target tensors
max_length_targ_zh2en, max_length_inp_zh2en = max_length(target_tensor_zh2en), max_length(input_tensor_zh2en)


# Creating training and validation sets using an 80-20 split
input_tensor_train_zh2en, input_tensor_val_zh2en, target_tensor_train_zh2en, target_tensor_val_zh2en = train_test_split(input_tensor_zh2en, target_tensor_zh2en, test_size=0.2)


BUFFER_SIZE_zh2en = len(input_tensor_train_zh2en)

steps_per_epoch_zh2en = len(input_tensor_train_zh2en)//BATCH_SIZE
vocab_inp_size_zh2en = len(inp_lang_zh2en.word_index)+1
vocab_tar_size_zh2en = len(targ_lang_zh2en.word_index)+1

dataset_zh2en = tf.data.Dataset.from_tensor_slices((input_tensor_train_zh2en, target_tensor_train_zh2en)).shuffle(BUFFER_SIZE_zh2en)
dataset_zh2en = dataset_zh2en.batch(BATCH_SIZE, drop_remainder=True)

encoder_zh2en, decoder_zh2en, optimizer_zh2en, loss_object_zh2en = creat_model(vocab_inp_size_zh2en, vocab_tar_size_zh2en, dataset_zh2en)

checkpoint_dir_zh2en = './checkpoint_zh2en'
checkpoint_prefix_zh2en = os.path.join(checkpoint_dir_zh2en, "ckpt")
checkpoint_zh2en = tf.train.Checkpoint(optimizer=optimizer_zh2en,
								 encoder=encoder_zh2en,
								 decoder=decoder_zh2en)
# restoring the latest checkpoint in checkpoint_dir
checkpoint_zh2en.restore(tf.train.latest_checkpoint(checkpoint_dir_zh2en))



# =================================== English To Chinese ======================================= 

def evaluate(sentence):
	attention_plot = np.zeros((max_length_targ, max_length_inp))

	sentence = preprocess_sentence(sentence)

	inputs = [inp_lang.word_index[i] for i in sentence.split(' ')]
	inputs = tf.keras.preprocessing.sequence.pad_sequences([inputs],
														   maxlen=max_length_inp,
														   padding='post')
	inputs = tf.convert_to_tensor(inputs)

	result = ''

	hidden = [tf.zeros((1, units))]
	enc_out, enc_hidden = encoder(inputs, hidden)

	dec_hidden = enc_hidden
	dec_input = tf.expand_dims([targ_lang.word_index['<start>']], 0)

	for t in range(max_length_targ):
		predictions, dec_hidden, attention_weights = decoder(dec_input,
															 dec_hidden,
															 enc_out)

		# storing the attention weights to plot later on
		attention_weights = tf.reshape(attention_weights, (-1, ))
		attention_plot[t] = attention_weights.numpy()

		predicted_id = tf.argmax(predictions[0]).numpy()

		result += targ_lang.index_word[predicted_id] + ' '

		if targ_lang.index_word[predicted_id] == '<end>':
			return result, sentence, attention_plot

		# the predicted ID is fed back into the model
		dec_input = tf.expand_dims([predicted_id], 0)

	return result, sentence, attention_plot

# Try experimenting with the size of that dataset
input_tensor, target_tensor, inp_lang, targ_lang = load_dataset(path_to_file, num_examples)

# Calculate max_length of the target tensors
max_length_targ, max_length_inp = max_length(target_tensor), max_length(input_tensor)


# Creating training and validation sets using an 80-20 split
input_tensor_train, input_tensor_val, target_tensor_train, target_tensor_val = train_test_split(input_tensor, target_tensor, test_size=0.2)


BUFFER_SIZE = len(input_tensor_train)

steps_per_epoch = len(input_tensor_train)//BATCH_SIZE
vocab_inp_size = len(inp_lang.word_index)+1
vocab_tar_size = len(targ_lang.word_index)+1

dataset = tf.data.Dataset.from_tensor_slices((input_tensor_train, target_tensor_train)).shuffle(BUFFER_SIZE)
dataset = dataset.batch(BATCH_SIZE, drop_remainder=True)

encoder, decoder, optimizer, loss_object = creat_model(vocab_inp_size, vocab_tar_size, dataset)

checkpoint_dir = './checkpoint_en2zh'
checkpoint_prefix = os.path.join(checkpoint_dir, "ckpt")
checkpoint = tf.train.Checkpoint(optimizer=optimizer,
								 encoder=encoder,
								 decoder=decoder)
# restoring the latest checkpoint in checkpoint_dir
checkpoint.restore(tf.train.latest_checkpoint(checkpoint_dir))



# ========================================= Flask APP ==========================================


app = Flask(__name__)

@app.route('/en2zh',methods=['POST'])
def translate_en2zh():
	sentence = request.get_json()['sentence']
	s = preprocess_sentence(sentence).split(' ')
	sentence = []
	for token in s[1:-1]:
		try:
			inp_lang.word_index[token]
			sentence.append(token)
		except Exception as e:
			sentence.append('?')
	result, sentence, attention_plot = evaluate(" ".join(sentence))
	return str({"status":200, "method":"Translate", "data":result.replace("<end>","").replace(" ","").strip()})

@app.route('/zh2en',methods=['POST'])
def translate_zh2en():
	sentence = request.get_json()['sentence']
	s = preprocess_sentence_zh(sentence).split(' ')
	sentence = []
	for token in s[1:-1]:
		try:
			inp_lang_zh2en.word_index[token]
			sentence.append(token)
		except Exception as e:
			sentence.append('？')
	result, sentence, attention_plot = evaluate_zh2en("".join(sentence))
	return str({"status":200, "method":"Translate", "data":result.replace("<end>","").strip()})
