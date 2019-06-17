package sim.netty;

import java.io.Serializable;

/**
 * The type Data content.
 */
public class DataContent implements Serializable {

    private static final long serialVersionUID = 8021381444738260454L;

    private Integer action;     //动作类型
    private ChatMsg chatMsg;    //用户的聊天内容entity
    private String extand;      //扩展字段

    /**
     * Gets action.
     *
     * @return the action
     */
    public Integer getAction() {
        return action;
    }

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * Gets chat msg.
     *
     * @return the chat msg
     */
    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    /**
     * Sets chat msg.
     *
     * @param chatMsg the chat msg
     */
    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    /**
     * Gets extand.
     *
     * @return the extand
     */
    public String getExtand() {
        return extand;
    }

    /**
     * Sets extand.
     *
     * @param extand the extand
     */
    public void setExtand(String extand) {
        this.extand = extand;
    }
}
