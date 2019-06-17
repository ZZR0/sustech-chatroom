package sim.pojo.vo;

/**
 * The type Friend request vo.
 *
 * @Description: 好友请求发送方的信息
 */
public class FriendRequestVO {
	
    private String sendUserId;
    private String sendUsername;
    private String sendNickname;
    private String sendGpa;

    /**
     * Gets send user id.
     *
     * @return the send user id
     */
    public String getSendUserId() {
		return sendUserId;
	}

    /**
     * Sets send user id.
     *
     * @param sendUserId the send user id
     */
    public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

    /**
     * Gets send username.
     *
     * @return the send username
     */
    public String getSendUsername() {
		return sendUsername;
	}

    /**
     * Sets send username.
     *
     * @param sendUsername the send username
     */
    public void setSendUsername(String sendUsername) {
		this.sendUsername = sendUsername;
	}

    /**
     * Gets send nickname.
     *
     * @return the send nickname
     */
    public String getSendNickname() {
		return sendNickname;
	}

    /**
     * Sets send nickname.
     *
     * @param sendNickname the send nickname
     */
    public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}

    /**
     * Gets send gpa.
     *
     * @return the send gpa
     */
    public String getSendGpa() {
		return sendGpa;
	}

    /**
     * Sets send gpa.
     *
     * @param sendGpa the send gpa
     */
    public void setSendGpa(String sendGpa) {
		this.sendGpa = sendGpa;
	}
}