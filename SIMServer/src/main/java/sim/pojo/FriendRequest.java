package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * The type Friend request.
 */
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @Column(name = "from_id")
    private String fromId;

    @Id
    @Column(name = "to_id")
    private String toId;

    private String msg;

    private Date date;

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
     * Gets date.
     *
     * @return date date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}