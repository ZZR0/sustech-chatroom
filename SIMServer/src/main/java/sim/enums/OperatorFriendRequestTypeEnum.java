package sim.enums;

/**
 * The enum Operator friend request type enum.
 *
 * @Description: 忽略或者通过 好友请求的枚举
 */
public enum OperatorFriendRequestTypeEnum {

	/**
	 * Ignore operator friend request type enum.
	 */
	IGNORE(0, "忽略"),
	/**
	 * Pass operator friend request type enum.
	 */
	PASS(1, "通过");

	/**
	 * The Type.
	 */
	public final Integer type;
	/**
	 * The Msg.
	 */
	public final String msg;
	
	OperatorFriendRequestTypeEnum(Integer type, String msg){
		this.type = type;
		this.msg = msg;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Gets msg by type.
	 *
	 * @param type the type
	 * @return the msg by type
	 */
	public static String getMsgByType(Integer type) {
		for (OperatorFriendRequestTypeEnum operType : OperatorFriendRequestTypeEnum.values()) {
			if (operType.getType() == type) {
				return operType.msg;
			}
		}
		return null;
	}
	
}
