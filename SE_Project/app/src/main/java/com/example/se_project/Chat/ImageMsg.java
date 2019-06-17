package com.example.se_project.Chat;

import com.alibaba.fastjson.JSONObject;
import com.example.se_project.AppData;
import com.example.se_project.Msg;
import com.example.se_project.R;

import java.util.Date;

/**
 * The type of image msg.
 */
public class ImageMsg extends Msg {
    private String SmallImage;
    private String BigImage;

    /**
     * Instantiates a new Image msg.
     *
     * @param content  the content
     * @param type     the type
     * @param sendTime the send time
     * @param msgId    the msg id
     */
    public ImageMsg(String content, int type, Date sendTime, String msgId) {
        super(content, type, sendTime, msgId);
        JSONObject image = JSONObject.parseObject(content);
        String serverUrl = AppData.getInstance().getContext().getString(R.string.FSFD_Server_Url);
        this.SmallImage = serverUrl + image.getString("SmallImage");
        this.BigImage = serverUrl + image.getString("BigImage");
    }

    /**
     * Gets small image.
     *
     * @return the small image
     */
    public String getSmallImage() {
        return SmallImage;
    }

    /**
     * Sets small image.
     *
     * @param smallImage the small image
     */
    public void setSmallImage(String smallImage) {
        SmallImage = smallImage;
    }

    /**
     * Gets big image.
     *
     * @return the big image
     */
    public String getBigImage() {
        return BigImage;
    }

    /**
     * Sets big image.
     *
     * @param bigImage the big image
     */
    public void setBigImage(String bigImage) {
        BigImage = bigImage;
    }
}
