package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * The type Moment content.
 */
@Table(name = "moment_content")
public class MomentContent {
    @Id
    @Column(name = "moment_id")
    private String momentId;

    @Column(name = "sender_id")
    private String senderId;

    private String content;

    @Column(name = "send_time")
    private Date sendTime;

    /**
     * Gets moment id.
     *
     * @return moment_id moment id
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
     * Gets sender id.
     *
     * @return sender_id sender id
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
     * Gets content.
     *
     * @return content content
     */
    public String getContent() {
        return content;
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
     * Gets send time.
     *
     * @return sendTime send time
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
}