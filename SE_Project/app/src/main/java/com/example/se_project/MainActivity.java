package com.example.se_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.se_project.Chat.WSClient;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Main activity of the application, login activity.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox userRemember, passwordRemember;
    private SharedPreferences sp;
    private TextView login_username, login_password;
    /**
     * The Alertdialog 1.
     */
    AlertDialog alertdialog1;
    @Override
    /**
     * {@inheritDoc}
     */
    protected void onCreate(Bundle savedInstanceState) {
        Button login, register,ensure_register;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login_button);
        register = findViewById(R.id.register_button);
        //ensure_register = findViewById(R.id.ensure_button);

        login_username = findViewById(R.id.username);
        login_password = findViewById(R.id.password);

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        boolean isUserRemember = sp.getBoolean("isUserRemember",false);
        boolean isPassworkRemember = sp.getBoolean("isPasswordRemember",false);

        userRemember = findViewById(R.id.Login_Remember_Usname);
        passwordRemember = findViewById(R.id.Login_Remember_Password);

        if(isUserRemember){
            login_username.setText(sp.getString("user_remember",""));
            userRemember.setChecked(true);
        }
        if(isPassworkRemember){
            login_password.setText(sp.getString("password_remember",""));
            passwordRemember.setChecked(true);
        }
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        //ensure_register.setOnClickListener(this);


    }

    @Override
    protected void onStart(){
        super.onStart();
        if (AppData.getInstance().getWsClient() != null){
            AppData.getInstance().getWsClient().close();
            AppData.getInstance().setWsClient(null);
        }
    }


    /**
     * To show wrong password dialog.
     */
    void showdialogWrongPassword()
    {
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("密码错误");
        alertdialogbuilder.setPositiveButton("确定", click1);
        alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }

    /**
     * To show username not exist dialog.
     */
    void showdialogNoUsername()
    {
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("用户名不存在");
        alertdialogbuilder.setPositiveButton("确定", click1);
        alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }

    /**
     * To show username not exist dialog.
     *
     * @param msg the msg
     */
    void showdialogMsg(String msg)
    {
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage(msg);
        alertdialogbuilder.setPositiveButton("确定", click1);
        alertdialog1=alertdialogbuilder.create();
        alertdialog1.show();
    }
    /**
     * When click "确定",cancle dialog.
     */
    private final DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
    {@Override

    public void onClick(DialogInterface arg0,int arg1)
    {
        arg0.cancel();
    }
    };

    @Override
    /**
     * {@inheritDoc}
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                //login in
                String usname = login_username.getText().toString();
                String psword = login_password.getText().toString();
                login(usname,psword);
                break;
            case R.id.register_button:
                //register
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.ensure_button:
                break;
            default:
                break;
        }
    }

    /**
     * Handle the login in message in {@link JSONObject} type.
     */
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject result = (JSONObject)msg.obj;

            switch (result.getIntValue("status")) {
                case 200:
                    //登录成功
                    SharedPreferences.Editor editor = sp.edit();
                    if(userRemember.isChecked()){
                        editor.putBoolean("isUserRemember",true);
                        editor.putString("user_remember",login_username.getText().toString());
                    }else{
                        editor.clear();
                    }

                    if(passwordRemember.isChecked()){
                        editor.putBoolean("isPasswordRemember",true);
                        editor.putString("password_remember",login_password.getText().toString());
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    JSONObject userInfo = JSONObject.parseObject(result.getString("data"));
                    AppData.getInstance().getMe().setId(userInfo.getString("id"));
                    AppData.getInstance().getMe().setName(userInfo.getString("username"));
                    AppData.getInstance().getMe().setGpa(userInfo.getDouble("gpa"));
                    AppData.getInstance().getMe().setNickName(userInfo.getString("nickname"));
                    AppData.getInstance().getFriendList().clear();
                    AppData.getInstance().getChatHistory().clear();
                    AppData.getInstance().setContext(getApplicationContext());
                    initFriends();
                    creadWebSocket();

                    getUnReadMsg();

                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, FriendActivity.class);
                    MainActivity.this.startActivity(intent1);
                    Log.d("result: ", result.getString("data"));
                    break;
                case 500:
                    //登陆失败
                    showdialogMsg(result.getString("msg"));
                    break;
                default:
                    Log.d("result", result.getString("msg"));
                    break;
            }
        }
    };

    /**
     * Get information of login and send to server.
     *
     * @param username the username
     * @param password the password
     */
    void login(final String username, final String password ) {
        final String request_url = this.getString(R.string.IM_Server_Url) + "/login";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    json_data.put("username", username);
                    json_data.put("password", password);

                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("login",result.toString());
                    handler.sendMessage(message);

                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Get information of login and send to server.
     */
    void getUnReadMsg() {
        final String request_url = this.getString(R.string.IM_Server_Url) + "/getUnReadMsgList?acceptUserId="
                + AppData.getInstance().getMe().getId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();

                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("login",result.toString());

                    JSONArray msgList = JSONArray.parseArray(result.getString("data"));
                    List<String> signList = new ArrayList<>();
                    for (Object item: msgList) {
                        JSONObject chatMsg = (JSONObject)item;
                        if (chatMsg.getString("toId").equals(AppData.getInstance().getMe().getId()))
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.CHINA);

                            int type = chatMsg.getIntValue("type");

                            if (type == 1){
                                AppData.getInstance().reciveImageMsg(chatMsg.getString("fromId"),
                                        chatMsg.getString("msg"),
                                        chatMsg.getString("msgId"),
                                        sdf.parse(chatMsg.getString("sendTime")));
                            }else {
                                AppData.getInstance().reciveChatMsg(chatMsg.getString("fromId"),
                                        chatMsg.getString("msg"),
                                        chatMsg.getString("msgId"),
                                        sdf.parse(chatMsg.getString("sendTime")));
                            }

                            signList.add(chatMsg.getString("msgId"));
                        }
                    }
                    if (!signList.isEmpty()){
                        AppData.getInstance().getWsClient().signMsg(signList);
                    }
                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","连接服务器失败...");
                    message.obj = result_json;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initFriends(){
        final String request_url = this.getString(R.string.IM_Server_Url) + "/myFriends?userId="+AppData.getInstance().getMe().getId();
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
                        System.out.println(jsonItem.toString());
                        User user = new User();
                        user.setId(jsonItem.getString("friendUserId"));
                        user.setGpa(jsonItem.getDouble("friendGpa"));
                        user.setName(jsonItem.getString("friendUsername"));
                        AppData.getInstance().getFriendList().add(user);

                    }

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
     * Cread web socket.
     */
    public void creadWebSocket() {
        Log.d("creadWebSocket","");
        try{
            URI Uri = new URI(this.getString(R.string.WebSocket_Server_Url));
            AppData.getInstance().setWsClient(new WSClient(Uri));
            AppData.getInstance().getWsClient().connect();
            Log.d("creadWebSocket","finish");
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
