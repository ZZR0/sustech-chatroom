package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Moment img.
 */
@Table(name = "moment_img")
public class MomentImg {
    @Id
    @Column(name = "moment_id")
    private String momentId;

    @Column(name = "img_path")
    private String imgPath;

    private Integer rank;

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
     * Gets img path.
     *
     * @return img_path img path
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Sets img path.
     *
     * @param imgPath the img path
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Gets rank.
     *
     * @return rank rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }
}