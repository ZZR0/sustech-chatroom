package com.example.se_project.Chat;

import com.example.se_project.Msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is used to save chat history.
 */
public class ChatHistory {

    private String friendId;
    private String myId;
    private Date lastTime;
    private List<Msg> msgList = new ArrayList<Msg>(){
        public boolean add(Msg e){
            lastTime = e.getSendTime();
            return super.add(e);
        }
    };

    /**
     * Gets friend id.
     *
     * @return the friend id
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * Sets friend id.
     *
     * @param friendId the friend id
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    /**
     * Gets my id.
     *
     * @return the my id
     */
    public String getMyId() {
        return myId;
    }

    /**
     * Sets my id.
     *
     * @param myId the my id
     */
    public void setMyId(String myId) {
        this.myId = myId;
    }

    /**
     * Gets last time.
     *
     * @return the last time
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * Sets last time.
     *
     * @param lastTime the last time
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Gets msg list.
     *
     * @return the msg list
     */
    public List<Msg> getMsgList() {
        return msgList;
    }

    /**
     * Sets msg list.
     *
     * @param msgList the msg list
     */
    public void setMsgList(List<Msg> msgList) {
        this.msgList = msgList;
    }
}

