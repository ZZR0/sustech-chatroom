package sim.netty;

import java.io.Serializable;

/**
 * The type Chat msg.
 */
public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 3611169682695799175L;
	
	private String senderId;		// 发送者的用户id	
	private String receiverId;		// 接受者的用户id
	private String msg;				// 聊天内容
	private String msgId;			// 用于消息的签收
	private int type = 0;			// 0:普通消息 1:图片消息

    /**
     * Gets sender id.
     *
     * @return the sender id
     */
    public String getSenderId() {
		return senderId;
	}

    /**
     * Sets sender id.
     *
     * @param senderId the sender id
     */
    public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

    /**
     * Gets receiver id.
     *
     * @return the receiver id
     */
    public String getReceiverId() {
		return receiverId;
	}

    /**
     * Sets receiver id.
     *
     * @param receiverId the receiver id
     */
    public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
		return msg;
	}

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
		this.msg = msg;
	}

    /**
     * Gets msg id.
     *
     * @return the msg id
     */
    public String getMsgId() {
		return msgId;
	}

    /**
     * Sets msg id.
     *
     * @param msgId the msg id
     */
    public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
		return type;
	}

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
		this.type = type;
	}
}
