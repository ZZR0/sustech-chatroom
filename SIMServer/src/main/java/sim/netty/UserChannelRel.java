package sim.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * The type User channel rel.
 *
 * @Description: 用户id和channel的关联关系处理
 */
public class UserChannelRel {

	private static HashMap<String, Channel> manager = new HashMap<>();

    /**
     * Put.
     *
     * @param senderId the sender id
     * @param channel  the channel
     */
    public static void put(String senderId, Channel channel) {
		manager.put(senderId, channel);
	}

    /**
     * Get channel.
     *
     * @param senderId the sender id
     * @return the channel
     */
    public static Channel get(String senderId) {
		return manager.get(senderId);
	}

    /**
     * Output.
     */
    public static void output() {
		for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
			System.out.println("UserId: " + entry.getKey() 
							+ ", ChannelId: " + entry.getValue().id().asLongText());
		}
	}

    /**
     * Remove string.
     *
     * @param Channel the channel
     * @return the string
     */
    public static String remove(String Channel){
		String userId = null;
		for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
			if(Channel.equals(entry.getValue().id().asLongText())){
				userId = entry.getKey();
				break;
			}
		}
		System.out.println("Remove UserId: " + userId
				+ ", ChannelId: " + Channel);
		if (userId != null){
			manager.remove(userId);
		}
		return userId;
	}
}
