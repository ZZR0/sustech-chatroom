package sim.DaoImpl;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sim.enums.MsgActionEnum;
import sim.enums.MsgSignFlagEnum;
import sim.mapper.ChatHistoryMapper;
import sim.mapper.FriendListMapper;
import sim.mapper.UsersMapper;
import sim.mapper.UsersMapperCustom;
import sim.mapper.FriendRequestMapper;
import sim.mapper.MomentContentMapper;
import sim.mapper.MomentThumbUpMapper;
import sim.netty.ChatMsg;
import sim.netty.DataContent;
import sim.netty.UserChannelRel;
import sim.pojo.FriendList;
import sim.pojo.FriendRequest;
import sim.pojo.MomentContent;
import sim.pojo.Users;
import sim.pojo.vo.FriendRequestVO;
import sim.pojo.vo.MomentContentVO;
import sim.pojo.vo.MyFriendsVO;
import sim.pojo.MomentThumbUp;
import sim.pojo.ChatHistory;
import sim.utils.JsonUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import sim.Dao.UserDao;
import sim.enums.SearchFriendsStatusEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is to implement UserDao interface.
 */
@Service
public class UserDaoImpl implements UserDao {

    @Autowired(required = false)
    private UsersMapper userMapper;
    @Autowired(required = false)
    private UsersMapperCustom usersMapperCustom;
    @Autowired(required = false)
    private ChatHistoryMapper chatHistoryMapper;
    @Autowired(required = false)
    private FriendListMapper friendListMapper;
    @Autowired(required = false)
    private FriendRequestMapper friendRequestMapper;
    @Autowired(required = false)
    private MomentContentMapper momentContentMapper;
    @Autowired(required = false)
    private MomentThumbUpMapper momentThumbUpMapper;

    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {

        // 根据用户名把朋友信息查询出来
        Users friend = queryUserInfoByUsername(friendUsername);

        // 1. 查询发送好友请求记录表
        Example fre = new Example(FriendRequest.class);
        Criteria frc = fre.createCriteria();
        frc.andEqualTo("fromId", myUserId);
        frc.andEqualTo("toId", friend.getId());
        FriendRequest friendRequest = friendRequestMapper.selectOneByExample(fre);
        if (friendRequest == null) {
            // 2. 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录

            FriendRequest request = new FriendRequest();

            request.setFromId(myUserId);
            request.setToId(friend.getId());
            request.setDate(new Date());
            request.setMsg("");
            friendRequestMapper.insert(request);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {
        Users user = queryUserInfoByUsername(friendUsername);
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        if (user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        Example mfe = new Example(FriendList.class);
        Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("ownerId", myUserId);
        mfc.andEqualTo("friendId", user.getId());
        FriendList myFriendsRel = friendListMapper.selectOneByExample(mfe);
        if (myFriendsRel != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkWhetherFriend(String userId1,String userId2){
        Example mfe = new Example(FriendList.class);
        Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("ownerId", userId1);
        mfc.andEqualTo("friendId", userId2);
        FriendList myFriendsRel = friendListMapper.selectOneByExample(mfe);
        return (myFriendsRel != null);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByUsername(String username) {
        Example ue = new Example(Users.class);
        Criteria uc = ue.createCriteria();
        uc.andEqualTo("username", username);
        return userMapper.selectOneByExample(ue);
    }

    /**
     * implement queryUsernameIsExist().
     *
     * @param username the username
     * @return the boolean
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);

        Users result = userMapper.selectOne(user);

        return result != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void deleteFriend(String userId1, String userId2) {
        Example mfe = new Example(FriendList.class);
        Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("ownerId", userId1);
        mfc.andEqualTo("friendId", userId2);
        FriendList myFriendsRel = friendListMapper.selectOneByExample(mfe);
        friendListMapper.delete(myFriendsRel);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MomentContentVO> pullMoment(String userId) {
        List<MomentContent> res = usersMapperCustom.viewMoment(userId);
        List<MomentContentVO> newRes = new ArrayList<>();
        for(MomentContent mc : res){
            MomentContentVO mcvo = new MomentContentVO();
            BeanUtils.copyProperties(mc,mcvo);
            System.out.println(mc.getMomentId());
            mcvo.setThumbUpList(usersMapperCustom.getMomentThumbUp(mc.getMomentId()));
            newRes.add(mcvo);
        }

        return newRes;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public MomentContent createMoment(MomentContent momentContent) {
        momentContentMapper.insert(momentContent);

        return momentContent;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public MomentThumbUp thumbUpMoment(MomentThumbUp momentThumbUp){
        momentThumbUpMapper.insert(momentThumbUp);

        return momentThumbUp;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean checkAlreadyThumbUp(MomentThumbUp momentThumbUp){
        return momentThumbUpMapper.selectOne(momentThumbUp) == null;
    }



    /**
     * implement queryUserForLogin()
     *
     * @param username the username
     * @param pwd      the pwd
     * @return the users
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", pwd);

        return userMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersMapperCustom.queryFriendRequestList(acceptUserId);
    }


    /**
     * Save friends.
     *
     * @param sendUserId   the send user id
     * @param acceptUserId the accept user id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFriends(String sendUserId, String acceptUserId) {
        FriendList myFriends = new FriendList();

        myFriends.setOwnerId(sendUserId);
        myFriends.setFriendId(acceptUserId);

        friendListMapper.insert(myFriends);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendRequest.class);
        Criteria frc = fre.createCriteria();
        frc.andEqualTo("fromId", sendUserId);
        frc.andEqualTo("toId", acceptUserId);
        friendRequestMapper.deleteByExample(fre);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);

        Channel sendChannel = UserChannelRel.get(sendUserId);
        if (sendChannel != null) {
            // 使用websocket主动推送消息到请求发起者，更新他的通讯录列表为最新
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);

            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContent)));
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        return usersMapperCustom.queryMyFriends(userId);
    }

    /**
     * implement createUser().
     *
     * @param user the user
     * @return the users
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(Users user) {
        userMapper.insert(user);

        return user;
    }
    /**
     * implement updateUserInfo();
     *
     * @param user the user
     * @return the users
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(Users user) {
        userMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    /**
     * Query user by id, if exist return that user.
     *
     * @param userId the user id
     * @return the users
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * @param chatMsg
     * @Description: 保存聊天消息到数据库
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(ChatMsg chatMsg) {

        ChatHistory msgDB = new ChatHistory();
        String msgId = Sid.nextShort();
        msgDB.setMsgId(msgId);
        msgDB.setToId(chatMsg.getReceiverId());
        msgDB.setFromId(chatMsg.getSenderId());
        msgDB.setSendTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDB.setMsg(chatMsg.getMsg());
        msgDB.setType(chatMsg.getType());

        chatHistoryMapper.insert(msgDB);

        return msgId;
    }

    /**
     * @param msgIdList
     * @Description: 批量签收消息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        System.out.println("updateMsgSigned");
        System.out.println(msgIdList);
        usersMapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    /**
     * @param toId
     * @Description: 获取未签收消息列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ChatHistory> getUnReadMsgList(String toId) {

        Example chatExample = new Example(ChatHistory.class);
        Criteria chatCriteria = chatExample.createCriteria();
        chatCriteria.andEqualTo("signFlag", 0);
        chatCriteria.andEqualTo("toId", toId);

        return chatHistoryMapper.selectByExample(chatExample);
    }
}
