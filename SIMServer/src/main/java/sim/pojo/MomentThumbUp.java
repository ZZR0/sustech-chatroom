package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Moment thumb up.
 */
@Table(name = "moment_thumb_up")
public class MomentThumbUp {
    @Id
    @Column(name = "moment_id")
    private String momentId;

    @Id
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