package sim.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Friend list.
 */
@Table(name = "friend_list")
public class FriendList {
    @Id
    @Column(name = "owner_id")
    private String ownerId;

    @Id
    @Column(name = "friend_id")
    private String friendId;

    private String tag;

    /**
     * Gets owner id.
     *
     * @return owner_id owner id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets owner id.
     *
     * @param ownerId the owner id
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Gets friend id.
     *
     * @return friend_id friend id
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
     * Gets tag.
     *
     * @return tag tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}