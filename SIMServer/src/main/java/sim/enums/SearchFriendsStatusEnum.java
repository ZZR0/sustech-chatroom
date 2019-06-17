package sim.enums;

/**
 * The enum Search friends status enum.
 *
 * @Description: 添加好友前置状态 枚举
 */
public enum SearchFriendsStatusEnum {

    /**
     * Success search friends status enum.
     */
    SUCCESS(0, "OK"),
    /**
     * User not exist search friends status enum.
     */
    USER_NOT_EXIST(1, "无此用户..."),
    /**
     * Not yourself search friends status enum.
     */
    NOT_YOURSELF(2, "不能添加你自己..."),
    /**
     * Already friends search friends status enum.
     */
    ALREADY_FRIENDS(3, "该用户已经是你的好友...");

    /**
     * The Status.
     */
    public final Integer status;
    /**
     * The Msg.
     */
    public final String msg;
	
	SearchFriendsStatusEnum(Integer status, String msg){
		this.status = status;
		this.msg = msg;
	}

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
		return status;
	}

    /**
     * Gets msg by key.
     *
     * @param status the status
     * @return the msg by key
     */
    public static String getMsgByKey(Integer status) {
		for (SearchFriendsStatusEnum type : SearchFriendsStatusEnum.values()) {
			if (type.getStatus() == status) {
				return type.msg;
			}
		}
		return null;
	}
	
}
