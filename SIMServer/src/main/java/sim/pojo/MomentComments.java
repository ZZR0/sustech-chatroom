package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Moment comments.
 */
@Table(name = "moment_comments")
public class MomentComments {
    @Id
    @Column(name = "moment_id")
    private String momentId;

    private String comment;

    @Column(name = "from_id")
    private String fromId;

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
     * Gets comment.
     *
     * @return comment comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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
}