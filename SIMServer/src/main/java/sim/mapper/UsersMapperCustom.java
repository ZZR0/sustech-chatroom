package sim.mapper;

import sim.pojo.MomentContent;
import sim.pojo.Users;
import sim.utils.MyMapper;
import sim.pojo.vo.*;
import java.util.List;

/**
 * The interface Users mapper custom.
 */
public interface UsersMapperCustom extends MyMapper<Users> {
    /**
     * Query friend request list list.
     *
     * @param acceptUserId the accept user id
     * @return the list
     */
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * Query my friends list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<MyFriendsVO> queryMyFriends(String userId);

    /**
     * View moment list.
     *
     * @param usedId the used id
     * @return the list
     */
    public List<MomentContent> viewMoment(String usedId);

    /**
     * Gets moment thumb up.
     *
     * @param momentId the moment id
     * @return the moment thumb up
     */
    public List<String> getMomentThumbUp(String momentId);

    /**
     * Batch update msg signed.
     *
     * @param msgIdList the msg id list
     */
    public void batchUpdateMsgSigned(List<String> msgIdList);

}