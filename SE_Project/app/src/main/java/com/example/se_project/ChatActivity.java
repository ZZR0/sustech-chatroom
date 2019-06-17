package com.example.se_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.se_project.Chat.ChatHistory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by cpj on 2016/3/15.
 * Modify it and implement chat in app.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The constant CONTEXTMENU_DELETEITEM.
     */
    protected static final int CONTEXTMENU_DELETEITEM = 0;
    /**
     * The constant CONTEXTMENU_TRANSLATION.
     */
    protected static final int CONTEXTMENU_TRANSLATION = 1;

    private ListView msgListView;
    private EditText inputText;
    private MsgAdapter adapter;
    List<Msg> msgList;
    private int pos;
    private User chatUser;
    private File mPhotoFile;
    private String mPhotoPath;
    private ImageView mImageView;
    private CameraFragment cameraFragment;
    private static final int REQUEST_SYSTEM_PIC = 1;
    private static final int CAMERA_RESULT = 2;


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppData.getInstance().setChatContext(this);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.chat_activity);
        chatUser=AppData.getInstance().getChattingFriend();
        initMsgs();//初始化消息数据
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_layout, msgList,chatUser);
        inputText = findViewById(R.id.input_msg);
        TextView NickName = findViewById(R.id.Chat_who);
        msgListView = findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        registerForContextMenu(msgListView);

        findViewById(R.id.Image).setOnClickListener(this);
        findViewById(R.id.send_msg).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);

        User me = AppData.getInstance().getMe();
        String name = me.getNickName();
//        int portraitID = me.getProfilePictureID();
        if (AppData.getInstance().getChattingFriend() != null)
            NickName.setText(AppData.getInstance().getChattingFriend().getNickName());
        else
            NickName.setText(AppData.getInstance().getMe().getNickName());
        initListView();
        AppData.getInstance().setChatHandler(handler);
    }


    /**
     * Refresh view.
     */
    public void refreshView(){
        adapter.notifyDataSetChanged();//当有消息时刷新
        msgListView.setSelection(msgList.size());//将ListView定位到最后一行
    }

    /**
     * Handle the chat in message in {@link JSONObject} type.
     */
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject result = (JSONObject)msg.obj;

            switch (result.getIntValue("status")) {
                case 200:
                    //Log.d("内容",content);
                    //得到翻译内容：
                    String translated = result.getString("data");
                    msgList.get(pos).setContent(translated);
                    refreshView();
                    break;
                case 500:
                    Log.d("result", result.getString("msg"));
                    break;
                case 800:
                    System.out.println("Chathandler: " + msgList.toString());
                    refreshView();
                    break;
                default:
                    Log.d("result", result.getString("msg"));
                    break;
            }
        }
    };

    /**
     * 初始化消息数据
     * */
    private void initMsgs(){
        ChatHistory history = AppData.getInstance().getChatHistory().get(chatUser.getId());
        if (history == null)
        {
            history = new ChatHistory();
            history.setFriendId(chatUser.getId());
            history.setMyId(AppData.getInstance().getMe().getId());
            history.setLastTime(new Date(System.currentTimeMillis()));
//            history.setMsgList(new ArrayList<Msg>());
            AppData.getInstance().getChatHistory().put(chatUser.getId(),history);
        }
        msgList = history.getMsgList();

    }



    private void initListView() {
        /* Loads the items to the ListView. */

        refreshView();

        /* Add Context-Menu listener to the ListView. */
        msgListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {
                //ChatActivity.super.onCreateContextMenu(conMenu,view,info);
               // AdapterView.AdapterContextMenuInfo am = (AdapterView.AdapterContextMenuInfo) info;

                conMenu.add(0, 0, 0, "删除");
                conMenu.add(1, 1, 1, "翻译");


                /* Add as many context-menu-options as you want to. */
            }
        });


      //暂时用不到，显示点击的内容
      /*  msgListView.setOnItemClickListener(new OnItemClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(ChatActivity.this, "111111111111", 200).show() ;
            }
        });*/

    }



    @Override
    public boolean onContextItemSelected(MenuItem aItem) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) aItem.getMenuInfo();

        /* Switch on the ID of the item, to get what the user selected. */
        switch (aItem.getItemId()) {
            case CONTEXTMENU_DELETEITEM:{
                /* Get the selected item out of the Adapter by its position. */
                int favContexted = (int) msgListView.getAdapter()
                        .getItemId(menuInfo.position);
                /* Remove it from the list.*/
                msgList.remove(favContexted);

                refreshView();

                return true; /* true means: "we handled the event". */
            }
            case CONTEXTMENU_TRANSLATION: {
                //请求翻译
                pos = menuInfo.position;
                String content = msgList.get(pos).getContent(); //待翻译内容
                //Log.d("内容",content);
                translate(content);
                break;
            }
            default:
                break;
        }
        return false;
    }


    @Override
    /**
     * {@inheritDoc}
     */
    public void onBackPressed() {
        if (cameraFragment==null) finish();
        else cameraFragment.onBackPressed();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Image:

                Intent intent = new Intent();
                intent.setClass(ChatActivity.this, UploadActivity.class);
                ChatActivity.this.startActivity(intent);
                break;
            case R.id.send_msg:
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    AppData.getInstance().sendChatMsg(content);
                    refreshView();
                    inputText.setText("");//清空输入框的内容
                }
                break;
            case R.id.camera:
                cameraFragment=CameraFragment.newInstance();
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                System.out.println(getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.outside, cameraFragment).addToBackStack(null).commit();
                break;
            default:
                break;
        }
    }

    /**
     * Get information of translate and send to NMT server.
     */
    private void translate(final String sentence) {
        final String server_url = this.getString(R.string.NMT_Server_Url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                String request_url = server_url;
                if (Utils.checkString(sentence))
                {
                    request_url += "/en2zh";
                }else{
                    request_url += "/zh2en";
                }
                try {
                    JSONObject json_data = new JSONObject();
                    json_data.put("sentence", sentence);

                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    handler.sendMessage(message);

                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","请求失败...");
                    message.obj = result_json;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * Gets msg list view.
     *
     * @return the msg list view
     */
    public ListView getMsgListView() {
        return msgListView;
    }

    /**
     * Sets msg list view.
     *
     * @param msgListView the msg list view
     */
    public void setMsgListView(ListView msgListView) {
        this.msgListView = msgListView;
    }

    /**
     * Gets adapter.
     *
     * @return the adapter
     */
    public MsgAdapter getAdapter() {
        return adapter;
    }

    /**
     * Sets adapter.
     *
     * @param adapter the adapter
     */
    public void setAdapter(MsgAdapter adapter) {
        this.adapter = adapter;
    }



}