package sim.enums;

/**
 * The enum Msg action enum.
 *
 * @Description: 发送消息的动作 枚举
 */
public enum MsgActionEnum {

	/**
	 * Connect msg action enum.
	 */
	CONNECT(1, "第一次(或重连)初始化连接"),
	/**
	 * Chat msg action enum.
	 */
	CHAT(2, "聊天消息"),
	/**
	 * Signed msg action enum.
	 */
	SIGNED(3, "消息签收"),
	/**
	 * Keepalive msg action enum.
	 */
	KEEPALIVE(4, "客户端保持心跳"),
	/**
	 * Pull friend msg action enum.
	 */
	PULL_FRIEND(5, "拉取好友");

	/**
	 * The Type.
	 */
	public final Integer type;
	/**
	 * The Content.
	 */
	public final String content;
	
	MsgActionEnum(Integer type, String content){
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
