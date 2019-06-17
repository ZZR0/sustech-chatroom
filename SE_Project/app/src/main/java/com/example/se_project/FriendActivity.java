package com.example.se_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * The type FriendActivity is used to show the user friend list.
 * It contains some button and a {@link ListView} to show friend.
 */
public class FriendActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView userListView;
    private EditText inputText;
    private UserAdapter adapter;
    private final List<User> userList = AppData.getInstance().getFriendList();

    protected void onStart(){
        super.onStart();
        refreshView();
    }

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.friend_activity);
        //initFriends();//初始化消息数据
        adapter = new UserAdapter(FriendActivity.this, R.layout.friend_list_layout, userList);
        userListView = findViewById(R.id.friend_list_view);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(FriendActivity.this,ChatActivity.class);
                User chatUser=(User)userListView.getItemAtPosition(position);
                intent.putExtra("ChatUser",chatUser);
                AppData.getInstance().setChattingFriend(chatUser);
                startActivity(intent);
            }
        });

        User me  = AppData.getInstance().getMe();
        String name = me.getNickName();
        int portait = me.getProfilePictureID();

        TextView NickName = findViewById(R.id.user_name2);
        ImageView Portrait = findViewById(R.id.user_portrait);

        NickName.setText(name);
        Portrait.setImageResource(portait);

        AppData.getInstance().setFriendHandler(handler);

        findViewById(R.id.search_friendlist).setOnClickListener(this);
        findViewById(R.id.moments).setOnClickListener(this);
        findViewById(R.id.add_activity).setOnClickListener(this);
    }


    /**
     * Refresh view.
     */
    public void refreshView(){
        adapter.notifyDataSetChanged();//当有消息时刷新
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
            if(result.getIntValue("status")==800){
                refreshView();
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_friendlist:
                AppData.getInstance().getMe().getFriendList();
                adapter.notifyDataSetChanged();
                break;
            case R.id.moments:
                Intent intent = new Intent(FriendActivity.this,MomentsActivity.class);
                startActivity(intent);
                break;
            case R.id.add_activity:
                Intent intent2 = new Intent(FriendActivity.this,FriendAddActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void searchFriendByName(String name){
    }

    private void startChat(User user){
        Intent intent = new Intent(FriendActivity.this,ChatActivity.class);
        startActivity(intent);
    }
}
