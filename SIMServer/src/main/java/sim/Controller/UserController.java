package sim.Controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sim.Dao.UserDao;
import sim.enums.MsgActionEnum;
import sim.enums.OperatorFriendRequestTypeEnum;
import sim.netty.ChatHandler;
import sim.netty.ChatMsg;
import sim.netty.DataContent;
import sim.netty.UserChannelRel;
import sim.pojo.ChatHistory;
import sim.pojo.MomentContent;
import sim.pojo.MomentThumbUp;
import sim.pojo.Users;
import sim.pojo.vo.ImageVO;
import sim.pojo.vo.MyFriendsVO;
import sim.pojo.vo.UsersVO;
import org.n3r.idworker.Sid;
import sim.enums.SearchFriendsStatusEnum;
import sim.utils.FastDFSClient;
import sim.utils.FileUtils;
import sim.utils.JSONResult;
import sim.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The Login and regist controller,witch is used to reply the request and update server.
 */
@RestController
public class UserController {

    /**
     * The constant loginList.
     */
    public static List<String> loginList = new ArrayList<>();

    @Autowired
    private UserDao userDao;
    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * Login json result.
     *
     * @param user the user
     * @return the json result
     * @throws Exception the exception
     */
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception{

        // 0. 判断用户名和密码不能为空
        if (user.getUsername() == null
                || user.getPassword() == null) {
            return JSONResult.errorMsg("用户名或密码不能为空...");
        }



        Users userResult = userDao.queryUserForLogin(user.getUsername(),
                user.getPassword());

        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确...");
        }else{
            if (loginList.contains(userResult.getId()))
                return JSONResult.errorMsg("用户已登录...");
            UsersVO userVO = new UsersVO();
            BeanUtils.copyProperties(userResult, userVO);
            loginList.add(userResult.getId());
            return JSONResult.ok(userVO);
        }

    }

    /**
     * Regist json result. Reply to request.
     *
     * @param user the user information
     * @return {@link JSONResult} message.
     * @throws Exception the exception
     */
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody Users user) throws Exception{

        // 0. 判断用户名和密码不能为空
        if (user.getUsername() == null
                || user.getPassword() == null) {
            return JSONResult.errorMsg("用户名或密码不能为空...");
        }

        Boolean userExit = userDao.queryUsernameIsExist(user.getUsername());
        if (userExit) {
            return JSONResult.errorMsg("用户已存在...");
        }else{
            user.setId(Sid.nextShort());
            user.setNickname(user.getUsername());
            Users userResult = userDao.createUser(user);
            if (userResult == null){
                return JSONResult.errorMsg("创建用户失败...");
            }else{
                UsersVO userVO = new UsersVO();
                BeanUtils.copyProperties(userResult, userVO);
                return JSONResult.ok(userVO);
            }
        }

    }

    /**
     * Search user with self id and friend user name.
     *
     * @param myUserId       id of current user
     * @param friendUsername the friend username
     * @return {@link JSONResult} message.
     * @throws Exception the exception
     */
    @PostMapping("/search")
    public JSONResult searchUser(String myUserId, String friendUsername)
            throws Exception {


        // 0. 判断 myUserId friendUsername 不能为空
        if (myUserId == null || friendUsername == null) {
            return JSONResult.errorMsg("");
        }

        Integer status = userDao.preconditionSearchFriends(myUserId, friendUsername);
        if (status.equals(SearchFriendsStatusEnum.SUCCESS.status)) {
            Users user = userDao.queryUserInfoByUsername(friendUsername);
            UsersVO userVO = new UsersVO();
            BeanUtils.copyProperties(user, userVO);
            return JSONResult.ok(userVO);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }
    }


    /**
     * Send friend request from from my user id to friend user name.
     *
     * @param myUserId       id of current user
     * @param friendUsername the friend username
     * @return {@link JSONResult} message.
     * @throws Exception the exception
     */
    @PostMapping("/addFriendRequest")
    public JSONResult addFriendRequest(String myUserId, String friendUsername)
            throws Exception {

        // 0. 判断 myUserId friendUsername 不能为空
        if (myUserId == null
                || friendUsername == null) {
            return JSONResult.errorMsg("");
        }
        System.out.println("addFriendRequest: "+myUserId+" "+friendUsername);
        // 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
        // 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
        // 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        Integer status = userDao.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            userDao.sendFriendRequest(myUserId, friendUsername);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }

        return JSONResult.ok();
    }


    /**
     * Send friend request from from my user id to friend user name.
     *
     * @param userId id of current user
     * @return {@link JSONResult} message.
     * @throws Exception the exception
     */
    @PostMapping("/queryFriendRequests")
    public JSONResult queryFriendRequests(String userId) {

        // 0. 判断不能为空
        if (userId==null) {
            return JSONResult.errorMsg("");
        }

        // 1. 查询用户接受到的朋友申请
        return JSONResult.ok(userDao.queryFriendRequestList(userId));
    }


    /**
     * Thumb up moment json result.
     *
     * @param momentThumbUp the moment thumb up
     * @return the json result
     */
    @PostMapping("/thumbUpMoment")
    public JSONResult thumbUpMoment(@RequestBody MomentThumbUp momentThumbUp){
        System.out.println(momentThumbUp.getFromId());
        System.out.println(momentThumbUp.getMomentId());
        if(momentThumbUp.getFromId() == null || momentThumbUp.getMomentId() == null){
            return JSONResult.errorMsg("");
        }
        if(userDao.checkAlreadyThumbUp(momentThumbUp)){
            userDao.thumbUpMoment(momentThumbUp);
        }
        return JSONResult.ok(momentThumbUp);
    }


    /**
     * Post moment json result.
     *
     * @param momentContent the moment content
     * @return the json result
     */
    @PostMapping("/postMoment")
    public JSONResult postMoment(@RequestBody MomentContent momentContent){
        if(momentContent.getSenderId() == null || momentContent.getContent() == null){
            return JSONResult.errorMsg("");
        }
        momentContent.setMomentId(Sid.nextShort());
        MomentContent momentResult = userDao.createMoment(momentContent);
        if(momentResult == null){
            return JSONResult.errorMsg("发布朋友圈失败...");
        }else{
            return JSONResult.ok(momentResult);
        }
    }

    /**
     * Pull moment json result.
     *
     * @param userId the user id
     * @return the json result
     */
    @PostMapping("/pullMoment")
    public JSONResult pullMoment(String userId){
        if(userId == null){
            return JSONResult.errorMsg("");
        }
        return JSONResult.ok(userDao.pullMoment(userId));
    }

    /**
     * Delete friend json result.
     *
     * @param userId1 the user id 1
     * @param userId2 the user id 2
     * @return the json result
     */
    @PostMapping("/deleteFriend")
    public JSONResult deleteFriend(String userId1,String userId2){
        System.out.println("deleteFriend "+userId1);
        System.out.println("deleteFriend "+userId2);
        if (userId1==null || userId2 == null) {
            return JSONResult.errorMsg("");
        }
        if(userDao.checkWhetherFriend(userId1,userId2) || userDao.checkWhetherFriend(userId2,userId1)){
            System.out.println("reach");
            userDao.deleteFriend(userId1,userId2);
            userDao.deleteFriend(userId2,userId1);
        }else{
            return JSONResult.errorMsg("Not your friends");
        }
        return JSONResult.ok();
    }

    /**
     * Send friend request from from my user id to friend user name.
     *
     * @param acceptUserId the user id of accepter
     * @param sendUserId   the user id of sender
     * @param operType     0 for decline;1 for accept
     * @return {@link JSONResult} message.
     * @throws Exception the exception
     */
    @PostMapping("/operFriendRequest")
    public JSONResult operFriendRequest(String acceptUserId, String sendUserId,
                                             Integer operType) {

        // 0. acceptUserId sendUserId operType 判断不能为空
        System.out.println("operFriendRequest");
        System.out.println(acceptUserId);
        System.out.println(sendUserId);
        System.out.println(operType);
        if (acceptUserId == null
                || sendUserId==null
                || operType == null) {
            return JSONResult.errorMsg("");
        }

        // 1. 如果operType 没有对应的枚举值，则直接抛出空错误信息
        if (OperatorFriendRequestTypeEnum.getMsgByType(operType)==null) {
            return JSONResult.errorMsg("");
        }

        if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
            // 2. 判断如果忽略好友请求，则直接删除好友请求的数据库表记录
            userDao.deleteFriendRequest(sendUserId, acceptUserId);
        } else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
            // 3. 判断如果是通过好友请求，则互相增加好友记录到数据库对应的表
            // 然后删除好友请求的数据库表记录
            userDao.passFriendRequest(sendUserId, acceptUserId);
        }

        // 4. 数据库查询好友列表
        List<MyFriendsVO> myFirends = userDao.queryMyFriends(acceptUserId);

        return JSONResult.ok(myFirends);
    }

    /**
     * My friends json result.
     *
     * @param userId the user id
     * @return the json result
     */
    @PostMapping("/myFriends")
    public JSONResult myFriends(String userId) {
        // 0. userId 判断不能为空
        if (userId == null) {
            return JSONResult.errorMsg("");
        }

        // 1. 数据库查询好友列表
        List<MyFriendsVO> myFirends = userDao.queryMyFriends(userId);

        return JSONResult.ok(myFirends);
    }

    /**
     * Gets un read msg list.
     *
     * @param acceptUserId the accept user id
     * @return the un read msg list
     * @Description: 用户手机端获取未签收的消息列表
     */
    @PostMapping("/getUnReadMsgList")
    public JSONResult getUnReadMsgList(String acceptUserId) {
        // 0. userId 判断不能为空
        if (StringUtils.isBlank(acceptUserId)) {
            return JSONResult.errorMsg("");
        }
        System.out.println("getUnReadMsgList"+acceptUserId);

        // 查询列表
        List<ChatHistory> unreadMsgList = userDao.getUnReadMsgList(acceptUserId);
        System.out.println(unreadMsgList.toString());
        return JSONResult.ok(unreadMsgList);
    }

    /**
     * Send base 64 json result.
     *
     * @param imageVO the image vo
     * @return the json result
     * @Description: 用户发送图片
     */
    @PostMapping("/sendBase64")
    public JSONResult sendBase64(@RequestBody ImageVO imageVO){
        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
        String base64Data = imageVO.getImageData();
        String userFacePath = "src/main/resources/image/" + imageVO.getUserId() + "chatImage64.png";
        try{
            FileUtils.base64ToFile(userFacePath, base64Data);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 上传文件到fastdfs
        String url = null;
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        try{
            url = fastDFSClient.uploadBase64(faceFile);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(url);

//        "dhawuidhwaiuh3u89u98432.png"
//        "dhawuidhwaiuh3u89u98432_80x80.png"

        // 获取缩略图的url
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String SmallImagUrl = arr[0] + thump + arr[1];

        JSONObject result = new JSONObject();
        result.put("BigImage",url);
        result.put("SmallImage",SmallImagUrl);

        // 保存消息到数据库，并且标记为 未签收
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setSenderId(imageVO.getUserId());
        chatMsg.setReceiverId(imageVO.getRecvId());
        chatMsg.setMsg(result.toString());
        chatMsg.setType(1);
        String msgId = userDao.saveMsg(chatMsg);
        chatMsg.setMsgId(msgId);

        DataContent dataContentMsg = new DataContent();
        dataContentMsg.setChatMsg(chatMsg);
        dataContentMsg.setAction(MsgActionEnum.CHAT.type);

        // 发送消息
        // 从全局用户Channel关系中获取接受方的channel
        Channel receiverChannel = UserChannelRel.get(imageVO.getRecvId());
        if (receiverChannel != null) {

            // 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
            Channel findChannel = ChatHandler.users.find(receiverChannel.id());
            if (findChannel != null) {
                // 用户在线
                receiverChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                JsonUtils.objectToJson(dataContentMsg)));
            }
        }

        return JSONResult.ok(result);
    }
}
