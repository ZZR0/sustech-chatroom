package sim.enums;

/**
 * The enum Msg sign flag enum.
 *
 * @Description: 消息签收状态 枚举
 */
public enum MsgSignFlagEnum {

	/**
	 * Unsign msg sign flag enum.
	 */
	unsign(0, "未签收"),
	/**
	 * Signed msg sign flag enum.
	 */
	signed(1, "已签收");

	/**
	 * The Type.
	 */
	public final Integer type;
	/**
	 * The Content.
	 */
	public final String content;
	
	MsgSignFlagEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}  
}
