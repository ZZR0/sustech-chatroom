package com.example.se_project;


import java.io.Serializable;
import java.util.Date;

/**
 * Moments class contains all the information of a moment.
 */
public class Moments implements Serializable {
    private User user;
    private String text;
    private String momentId;
    private int goodNum;

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

    private Date sendTime;

    /**
     * Instantiates a new Moments.
     *
     * @param user    the user
     * @param text    the text
     * @param goodNum the good num
     */
    public Moments(User user, String text,int goodNum){
        this.user=user;
        this.text=text;
        this.goodNum=goodNum;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Get text string.
     *
     * @return the string
     */
    public String getText(){
        return text;
    }

    /**
     * Add good.
     */
    public void addGood(){
        this.goodNum++;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets moment id.
     *
     * @return the moment id
     */
    public String getMomentId() {
        return momentId;
    }

    /**
     * Sets moment id.
     *
     * @param momentId the moment id
     */
    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    /**
     * Gets good num.
     *
     * @return the good num
     */
    public int getGoodNum() {
        return goodNum;
    }

    /**
     * Sets good num.
     *
     * @param goodNum the good num
     */
    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }




}
