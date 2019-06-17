package sim.Dao;

import sim.netty.ChatMsg;
import sim.pojo.ChatHistory;
import sim.pojo.MomentContent;
import sim.pojo.MomentThumbUp;
import sim.pojo.Users;
import sim.pojo.vo.FriendRequestVO;
import sim.pojo.vo.MomentContentVO;
import sim.pojo.vo.MyFriendsVO;

import java.util.List;

/**
 * The interface User dao, to provide the interface of database access.
 */
public interface UserDao {

    /**
     * if Query username is exist return false, or true.
     *
     * @param username the username
     * @return the boolean
     * @Description: judge if the user name is already existed
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * Query user name in database..
     *
     * @param username the username
     * @param pwd      the pwd
     * @return {@link Users}
     * @Description: search for the user name and return the user if exist.
     */
    public Users queryUserForLogin(String username, String pwd);

    /**
     * Create user users.
     *
     * @param user the user
     * @return the users
     * @Description: register user
     */
    public Users createUser(Users user);

    /**
     * Pull moment list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<MomentContentVO> pullMoment(String userId);

    /**
     * Create moment moment content.
     *
     * @param momentContent the moment content
     * @return the moment content
     */
    public MomentContent createMoment(MomentContent momentContent);

    /**
     * Check already thumb up boolean.
     *
     * @param momentThumbUp the moment thumb up
     * @return the boolean
     */
    public boolean checkAlreadyThumbUp(MomentThumbUp momentThumbUp);

    /**
     * Update user info users.
     *
     * @param user the user
     * @return the users
     * @Description: change user information
     */
    public Users updateUserInfo(Users user);

    /**
     * Thumb up moment moment thumb up.
     *
     * @param momentThumbUp the moment thumb up
     * @return the moment thumb up
     */
    public MomentThumbUp thumbUpMoment(MomentThumbUp momentThumbUp);

    /**
     * Query user by id users.
     *
     * @param userId the user id
     * @return the users
     */
    public Users queryUserById(String userId);

    /**
     * Precondition search friends integer.
     *
     * @param myUserId       the my user id
     * @param friendUsername the friend username
     * @return the integer
     */
    public Integer preconditionSearchFriends(String myUserId, String friendUsername);

    /**
     * Query user info by username users.
     *
     * @param username the username
     * @return the users
     */
    public Users queryUserInfoByUsername(String username);

    /**
     * Send friend request.
     *
     * @param myUserId       the my user id
     * @param friendUsername the friend username
     */
    public void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * Query friend request list list.
     *
     * @param acceptUserId the accept user id
     * @return the list
     */
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * Delete friend request.
     *
     * @param sendUserId   the send user id
     * @param acceptUserId the accept user id
     */
    public void deleteFriendRequest(String sendUserId, String acceptUserId);

    /**
     * Pass friend request.
     *
     * @param sendUserId   the send user id
     * @param acceptUserId the accept user id
     */
    public void passFriendRequest(String sendUserId, String acceptUserId);

    /**
     * Query my friends list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<MyFriendsVO> queryMyFriends(String userId);

    /**
     * Save msg string.
     *
     * @param chatMsg the chat msg
     * @return the string
     * @Description: 保存聊天消息到数据库
     */
    public String saveMsg(ChatMsg chatMsg);

    /**
     * Delete friend.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     */
    public void deleteFriend(String userId1, String userId2);

    /**
     * Check whether friend boolean.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @return the boolean
     */
    public boolean checkWhetherFriend(String userId1,String userId2);

    /**
     * Update msg signed.
     *
     * @param msgIdList the msg id list
     * @Description: 批量签收消息
     */
    public void updateMsgSigned(List<String> msgIdList);


    /**
     * Gets un read msg list.
     *
     * @param acceptUserId the accept user id
     * @return the un read msg list
     * @Description: 获取未签收消息列表
     */
    public List<ChatHistory> getUnReadMsgList(String acceptUserId);
}
