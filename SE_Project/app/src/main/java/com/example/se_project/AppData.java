package com.example.se_project;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.example.se_project.Chat.ChatHistory;
import com.example.se_project.Chat.ImageMsg;
import com.example.se_project.Chat.WSClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type AppData save the user information and app information in app.
 * It also contains some methods to request things from server.
 */
public class AppData {

    private User me = new User();
    private WSClient wsClient;
    private User chattingFriend = new User();
    private Map<String, ChatHistory> chatHistory = new HashMap<>();
    private List<User> friendList = new ArrayList<>();
    private Handler chatHandler;
    private List<Moments> momentsList;
    private Handler friendHandler;
    private Handler momentsHandler;
    private Handler friendAddHandler;



    private Context context;
    private Context chatContext;

    private static AppData singletonData = new AppData();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AppData getInstance() {
        return singletonData;
    }


    /**
     * Gets friend.
     *
     * @param userId the user id
     * @return the friend
     */
    public User getFriend(String userId) {
        if (userId.equals(me.getId())) {
            return me;
        } else {
            for (User tmp : friendList) {
                if (tmp.getId().equals(userId)) {
                    return tmp;
                }
            }
            return null;
        }
    }


    private void refreshChat() {
        if (chatHandler == null)
            return;
        Message message = new Message();
        JSONObject result_json = new JSONObject();
        result_json.put("status", 800);
        message.obj = result_json;
        chatHandler.sendMessage(message);
    }

    /**
     * Gets moments list.
     *
     * @return the moments list
     */
    public List<Moments> getMomentsList() {
        return momentsList;
    }

    /**
     * Send chat msg.
     *
     * @param msg the msg
     */
    public void sendChatMsg(String msg) {
        ChatHistory history = chatHistory.get(chattingFriend.getId());
        if (wsClient.isOpen() && chattingFriend.getId() != null) {
            wsClient.sendMsg(chattingFriend.getId(), msg);
            Msg m = new Msg(msg, Msg.TYPE_SENT, new Date(System.currentTimeMillis()), null);
            history.getMsgList().add(m);
        }
        refreshChat();
    }

    /**
     * Recive chat msg.
     *
     * @param friendId the friend id
     * @param msg      the msg
     * @param msgId    the msg id
     * @param time     the time
     */
    public void reciveChatMsg(String friendId, String msg, String msgId, Date time) {
        ChatHistory history = chatHistory.get(friendId);
        if (history == null) {
            history = new ChatHistory();
            history.setFriendId(friendId);
            history.setMyId(me.getId());
            history.setLastTime(time);
//            history.setMsgList(new ArrayList<Msg>());
            chatHistory.put(friendId, history);
        }
        System.out.println("reciveChatMsg: " + msg);
        Msg m = new Msg(msg, Msg.TYPE_RECEIVED, time, msgId);
        history.getMsgList().add(m);
        refreshChat();
    }

    /**
     * Recive chat msg.
     *
     * @param friendId the friend id
     * @param msg      the msg
     * @param msgId    the msg id
     */
    public void reciveChatMsg(String friendId, String msg, String msgId) {
        ChatHistory history = chatHistory.get(friendId);
        if (history == null) {
            history = new ChatHistory();
            history.setFriendId(friendId);
            history.setMyId(me.getId());
            history.setLastTime(new Date(System.currentTimeMillis()));
//            history.setMsgList(new ArrayList<Msg>());
            chatHistory.put(friendId, history);
        }

        Msg m = new Msg(msg, Msg.TYPE_RECEIVED, msgId);
        history.getMsgList().add(m);
        refreshChat();
    }

    /**
     * Send image msg.
     *
     * @param msg the msg
     */
    public void sendImageMsg(String msg) {
        ChatHistory history = chatHistory.get(chattingFriend.getId());

        ImageMsg m = new ImageMsg(msg, Msg.TYPE_SENT, new Date(System.currentTimeMillis()), null);
        history.getMsgList().add(m);
        refreshChat();
    }

    /**
     * Recive image msg.
     *
     * @param friendId the friend id
     * @param msg      the msg
     * @param msgId    the msg id
     * @param time     the time
     */
    public void reciveImageMsg(String friendId, String msg, String msgId, Date time) {

        if (time == null)
            time = new Date(System.currentTimeMillis());
        ChatHistory history = chatHistory.get(friendId);
        if (history == null) {
            history = new ChatHistory();
            history.setFriendId(friendId);
            history.setMyId(me.getId());
            history.setLastTime(time);
            chatHistory.put(friendId, history);
        }
        System.out.println("reciveImageMsg: " + msg);
        Msg m = new ImageMsg(msg, Msg.TYPE_RECEIVED, time, msgId);
        history.getMsgList().add(m);
        refreshChat();
    }

    /**
     * Gets me.
     *
     * @return the me
     */
    public User getMe() {
        return me;
    }

    /**
     * Gets ws client.
     *
     * @return the ws client
     */
    public WSClient getWsClient() {
        return wsClient;
    }

    /**
     * Sets ws client.
     *
     * @param wsClient the ws client
     */
    public void setWsClient(WSClient wsClient) {
        this.wsClient = wsClient;
    }

    /**
     * Sets me.
     *
     * @param me the me
     */
    public void setMe(User me) {
        this.me = me;
    }

    /**
     * Gets friend list.
     *
     * @return the friend list
     */
    public List<User> getFriendList() {
        return friendList;
    }

    /**
     * Sets friend list.
     *
     * @param friendList the friend list
     */
    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    /**
     * Gets chatting friend.
     *
     * @return the chatting friend
     */
    public User getChattingFriend() {
        return chattingFriend;
    }

    /**
     * Sets chatting friend.
     *
     * @param chattingFriend the chatting friend
     */
    public void setChattingFriend(User chattingFriend) {
        this.chattingFriend = chattingFriend;
    }

    /**
     * Gets chat history.
     *
     * @return the chat history
     */
    public Map<String, ChatHistory> getChatHistory() {
        return chatHistory;
    }

    /**
     * Sets chat history.
     *
     * @param chatHistory the chat history
     */
    public void setChatHistory(Map<String, ChatHistory> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Gets chat handler.
     *
     * @return the chat handler
     */
    public Handler getChatHandler() {
        return chatHandler;
    }

    /**
     * Sets chat handler.
     *
     * @param chatHandler the chat handler
     */
    public void setChatHandler(Handler chatHandler) {
        this.chatHandler = chatHandler;
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets context.
     *
     * @param context the context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets chat context.
     *
     * @return the chat context
     */
    public Context getChatContext() {
        return chatContext;
    }

    /**
     * Sets chat context.
     *
     * @param chatContext the chat context
     */
    public void setChatContext(Context chatContext) {
        this.chatContext = chatContext;
    }

    /**
     * Gets friend handler.
     *
     * @return the friend handler
     */
    public Handler getFriendHandler() {
        return friendHandler;
    }

    /**
     * Sets friend handler.
     *
     * @param friendHandler the friend handler
     */
    public void setFriendHandler(Handler friendHandler) {
        this.friendHandler = friendHandler;
    }

    /**
     * Gets friend add handler.
     *
     * @return the friend add handler
     */
    public Handler getFriendAddHandler() {
        return friendAddHandler;
    }

    /**
     * Sets friend add handler.
     *
     * @param friendAddHandler the friend add handler
     */
    public void setFriendAddHandler(Handler friendAddHandler) {
        this.friendAddHandler = friendAddHandler;
    }

    /**
     * Gets moments handler.
     *
     * @return the moments handler
     */
    public Handler getMomentsHandler () {
        return momentsHandler;
    }

    /**
     * Set moments handler.
     *
     * @param momentsHandler the moments handler
     */
    public void setMomentsHandler (Handler momentsHandler){
        this.momentsHandler = momentsHandler;

    }

}
