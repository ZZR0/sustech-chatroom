package sim.pojo.vo;

/**
 * The type Image vo.
 */
public class ImageVO {
    private String userId;
    private String recvId;
    private String imageData;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets recv id.
     *
     * @return the recv id
     */
    public String getRecvId() {
        return recvId;
    }

    /**
     * Sets recv id.
     *
     * @param recvId the recv id
     */
    public void setRecvId(String recvId) {
        this.recvId = recvId;
    }

    /**
     * Gets image data.
     *
     * @return the image data
     */
    public String getImageData() {
        return imageData;
    }

    /**
     * Sets image data.
     *
     * @param imageData the image data
     */
    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
