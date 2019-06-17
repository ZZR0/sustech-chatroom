package com.example.se_project;

import java.util.Date;

/**
 * Created by cpj on 2016/3/15.
 * Modify this class to adapt to chat activity
 */
public class Msg {
    /**
     * The constant TYPE_RECEIVED.
     */
    public static final int TYPE_RECEIVED = 0;//收到的消息
    /**
     * The constant TYPE_SENT.
     */
    public static final int TYPE_SENT = 1;//发送的消息
    private String content;//消息内容
    private  int type;//消息类型
    private Date sendTime;
    private String msgId;

    /**
     * Instantiates a new Msg.
     *
     * @param content the content
     * @param type    the type
     */
    public  Msg(String content, int type){
        this.content = content;
        this.type = type;
        this.sendTime = new Date(System.currentTimeMillis());
    }

    /**
     * Instantiates a new Msg.
     *
     * @param content  the content
     * @param type     the type
     * @param sendTime the send time
     * @param msgId    the msg id
     */
    public Msg(String content, int type, Date sendTime, String msgId) {
        this.content = content;
        this.type = type;
        this.sendTime = sendTime;
        this.msgId = msgId;
    }

    /**
     * Instantiates a new Msg.
     *
     * @param content the content
     * @param type    the type
     * @param msgId   the msg id
     */
    public Msg(String content, int type, String msgId) {
        this.content = content;
        this.type = type;
        this.msgId = msgId;
        this.sendTime = new Date(System.currentTimeMillis());
    }

    /**
     * Gets send time.
     *
     * @return the send time
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * Sets send time.
     *
     * @param sendTime the send time
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * Instantiates a new Msg.
     */
    public Msg(){
        this.content = null;
        this.type = 0;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
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
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Set type.
     *
     * @param type the type
     */
    public void setType(int type){
        this.type = type;
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
}