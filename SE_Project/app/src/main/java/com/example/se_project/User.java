package com.example.se_project;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The type User saving user information.
 */
public class User implements Serializable {
    private String name;
    private double gpa;
    private String id;
    private String nickName;
    private int potraitnum; //头像ID
    private int profilePictureID;
    private final List<User> friendList=new ArrayList<>();
    private final ArrayList<Moments> momentsList=new ArrayList<>();

    /**
     * Instantiates a new User.
     */
    public User(){

    }

    /**
     * Instantiates a new User.
     *
     * @param name the name
     * @param gpa  the gpa
     */
    public User(String name,double gpa){
        this.gpa=gpa;
        this.name=name;
        this.setPotraitnum();
        this.setProfilePictureID();
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return name;
    }

    /**
     * Get gpa double.
     *
     * @return the double
     */
    public double getGpa(){
        return gpa;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets gpa.
     *
     * @param gpa the gpa
     */
    public void setGpa(double gpa) {
        this.gpa = gpa;
        this.setPotraitnum();
        this.setProfilePictureID();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    private void setPotraitnum(){
        if (this.gpa>=3.7){
            potraitnum = 3;
        }else if (this.gpa>=3.2){
            potraitnum = 2;
        }else if (this.gpa>=2.8){
            potraitnum = 1;
        }else{
            potraitnum = 0;
        }
    }
    private void setProfilePictureID(){
        profilePictureID= R.drawable.gpa0;
        if(potraitnum==3)
            profilePictureID=R.drawable.gpa3;
        else if(potraitnum==2)
            profilePictureID=R.drawable.gpa2;
        else if(potraitnum==1)
            profilePictureID=R.drawable.gpa1;
        else if(potraitnum==0)
            profilePictureID=R.drawable.gpa0;
    }

    /**
     * Get profile picture id int.
     *
     * @return the int
     */
    public int getProfilePictureID(){
        return profilePictureID;
    }

    /**
     * Get potraitnum int.
     *
     * @return the int
     */
    public int getPotraitnum(){
        return potraitnum;
    }

    /**
     * Add friend boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean addFriend(User user){
        final String request_url = "http://10.21.72.100:8081" + "/addFriendRequest?myUserId="+AppData.getInstance().getMe().getId()+"&friendUsername="+user.getName();
        final String request_url_1 = "http://10.21.72.100:8081" + "/search?myUserId="+AppData.getInstance().getMe().getId()
                +"&friendUsername="+user.getName();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("111    ",result.toString());
                    if(result.getInteger("status")!= 200){
                        Log.d("111    ",result.toString());
                        return;
                    }
                    Log.d("555    ",result.toString());


                    Message message1 = new Message();
                    JSONObject json_data1 = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url_1, json_data1);
                    JSONObject result1 = (JSONObject)message.obj;

                    Log.d("556     ",result1.toString());
                    String accept = JSONObject.parseObject(result1.getString("data")).getString("id");

                    Log.d("557     ",accept.toString());
                    String request_url_2 = "http://10.21.72.100:8081"+ "/operFriendRequest?acceptUserId="+accept
                            +"&sendUserId="+AppData.getInstance().getMe().getId()+"&operType=1";
                    Message message2 = new Message();
                    JSONObject json_data2 = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url_2, json_data2);
                    refreshFriendList();
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    /**
     * Delete friend boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean deleteFriend(User user){
        final String request_url = "http://10.21.72.100:8081" + "/deleteFriend?userId1="+AppData.getInstance().getMe().getId()+"&userId2="+user.getId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("111    ",result.toString());
                    if(result.getInteger("status")!= 200){
                        Log.d("111    ",result.toString());
                        return;
                    }
                    Log.d("555    ",result.toString());

                    refreshFriendList();
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    /**
     * Search friend array list.
     *
     * @param name the name
     * @return the array list
     */
/*public boolean deleteFriend(User user){
        this.refreshFriendList();
        List<User> list= AppData.getInstance().getFriendList();
        for(int i=0;i<list.size();i++) {
            if (user.getId() == list.get(i).getId()) {
                list.remove(i);
                //add
                return true;
            }
        }
        return false;
    }*/
    public ArrayList<User> searchFriend(String name){
        this.refreshFriendList();
        Pattern pattern=Pattern.compile(name);
        Matcher matcher;
        ArrayList<User> resultList=new ArrayList<>();
        List<User> list= AppData.getInstance().getFriendList();
        for(int i=0;i<list.size();i++) {
            matcher = pattern.matcher((list.get(i)).getName());
            if (matcher.find()) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }

    /**
     * Get friend list list.
     *
     * @return the list
     */
    protected List<User> getFriendList(){
        this.refreshFriendList();
        return friendList;
    }

    private void refreshFriends(){
        if (AppData.getInstance().getFriendHandler() == null)
            return;
        Message message = new Message();
        JSONObject result_json = new JSONObject();
        result_json.put("status",800);
        message.obj = result_json;
        AppData.getInstance().getFriendHandler().sendMessage(message);
    }

    private void refreshFriendList(){
        final String request_url = "http://10.21.72.100:8081" + "/myFriends?userId="+AppData.getInstance().getMe().getId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("get friend list",result.toString());
                    JSONArray jsonArray = (JSONArray) JSONArray.parse(result.getString("data"));

                    AppData.getInstance().getFriendList().clear();
                    for (Object item:jsonArray) {
                        JSONObject jsonItem = (JSONObject)item;
                        User user = new User();
                        user.setId(jsonItem.getString("friendUserId"));
                        user.setGpa(jsonItem.getDouble("friendGpa"));
                        user.setName(jsonItem.getString("friendUsername"));
                        AppData.getInstance().getFriendList().add(user);
                    }
                    Log.d("refreshFriendList",AppData.getInstance().getFriendList().toString());
                    refreshFriends();
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Add good.
     *
     * @param moment the moment
     */
    protected void addGood(Moments moment){
        moment.addGood();
    }

    /**
     * Send moments.
     *
     * @param text the text
     */
    protected void sendMoments(String text){
        if(text == null || text.length() == 0)
            return;
        Moments moment = new Moments(this,text,0);
        final String request_url = "http://10.21.72.100:8081" + "/postMoment";
        final String content = text;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");//设置日期格式
                    JSONObject json_data = new JSONObject();
                    json_data.put("senderId", AppData.getInstance().getMe().id);
                    json_data.put("content", content);
                    json_data.put("sendTime",df.format(new Date()).toString());
                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    AppData.getInstance().getMomentsHandler().sendMessage(message);
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status", 0);
                    result_json.put("msg", "连接服务器失败...");
                    message.obj = result_json;
                    AppData.getInstance().getMomentsHandler().sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Refresh moments list.
     */
    protected void refreshMomentsList(){
        momentsList.clear();
        final String request_url = "http://10.21.72.100:8081" + "/pullMoment?userId="+AppData.getInstance().getMe().id;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    JSONArray jsonArray = (JSONArray) JSONArray.parse(result.getString("data"));
                    for (Object item:jsonArray) {
                        JSONObject jsonItem = (JSONObject)item;
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                        Moments moments = new Moments(AppData.getInstance().getFriend(jsonItem.getString("senderId")),jsonItem.getString("content"),0);
                        JSONArray jsonArraytmp = (JSONArray) JSONArray.parse(jsonItem.getString("thumbUpList"));
                        moments.setGoodNum(jsonArraytmp.size());
                        moments.setMomentId(jsonItem.getString("momentId"));
                        moments.setSendTime(df.parse(jsonItem.getString("sendTime")));
                        momentsList.add(moments);
                    }
                    Message msg = new Message();
                    JSONObject json = new JSONObject();
                    json.put("status",800);
                    msg.obj = json;
                    AppData.getInstance().getMomentsHandler().sendMessage(msg);
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Get information of addGood and send to server.
     *
     * @param momentId the moment id
     */
    void thumbUpMoment( final  String momentId) {
        final String request_url = AppData.getInstance().getContext().getString(R.string.IM_Server_Url) + "/thumbUpMoment";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    json_data.put("momentId", momentId);
                    json_data.put("fromId", AppData.getInstance().getMe().getId());

                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("thumbUpMoment",result.toString());
                    AppData.getInstance().getMomentsHandler().sendMessage(message);
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    AppData.getInstance().getMomentsHandler().sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Get moments list list.
     *
     * @return the list
     */
    public List<Moments> getMomentsList(){
        return momentsList;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
