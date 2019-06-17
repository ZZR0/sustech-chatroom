package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * The type Chat history.
 */
@Table(name = "chat_history")
public class ChatHistory {
    @Id
    @Column(name = "msg_id")
    private String msgId;

    @Column(name = "from_id")
    private String fromId;

    @Column(name = "to_id")
    private String toId;

    private String msg;

    @Column(name = "send_time")
    private Date sendTime;

    @Column(name = "sign_flag")
    private Integer signFlag;

    private Integer type;

    /**
     * Gets msg id.
     *
     * @return msg_id msg id
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
     * Gets from id.
     *
     * @return from_id from id
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * Sets from id.
     *
     * @param fromId the from id
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * Gets to id.
     *
     * @return to_id to id
     */
    public String getToId() {
        return toId;
    }

    /**
     * Sets to id.
     *
     * @param toId the to id
     */
    public void setToId(String toId) {
        this.toId = toId;
    }

    /**
     * Gets msg.
     *
     * @return msg msg
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
     * Gets send time.
     *
     * @return send_time send time
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
     * Gets sign flag.
     *
     * @return sign_flag sign flag
     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * Sets sign flag.
     *
     * @param signFlag the sign flag
     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * Gets type.
     *
     * @return type type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Integer type) {
        this.type = type;
    }
}