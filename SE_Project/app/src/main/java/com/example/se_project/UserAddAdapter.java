package com.example.se_project;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * This adapter is used in list view in add friend activity.
 */
public class UserAddAdapter extends ArrayAdapter<User>{

    private final int resourceId;
    private final UserAddAdapter userAddAdapter=this;

    /**
     * Instantiates a new User add adapter.
     *
     * @param context            the context
     * @param textViewResourceId the text view resource id
     * @param objects            the objects
     */
    public UserAddAdapter(Context context, int textViewResourceId,
                          List<User> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    /**
     * Function Description: This method is called when each subitem scrolls to the screen
     *
     * 1. First, get the ToolBar instance of the current item through getItem ().
     *
     * 2. Use Layout Inflate to load and pass this sub-layout item into our main layout
     *
     * 3. Call FindViewById () of View to get examples of left and right Layout and Msg respectively.
     *
     * 4. Call their setText () to display text
     *
     * 5. Final return to layout
     * */
    public View getView(int position, View convertView, ViewGroup parent){
        User user = getItem(position);
        View view;
        ViewHolder viewHolder;

        /*加载自定义布局与控件实例*/
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            //创建控件实例并进行缓存
            viewHolder = new ViewHolder();
            viewHolder.layout = (LinearLayout) view.findViewById(R.id.user_add_layout);
            viewHolder.name = (TextView) view.findViewById(R.id.user_name);
            viewHolder.button=(Button) view.findViewById(R.id.add_request);
            viewHolder.image=(ImageView) view.findViewById(R.id.add_user_image) ;
            view.setTag(viewHolder);

        } else {
            //convertView参数用于将之前加载好的布局进行缓存，以便之后可以进行复用（提高效率）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /*接受与发送消息的分类处理*/
        //如果为收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
        viewHolder.layout.setVisibility(View.VISIBLE);
        viewHolder.name.setText(user.getName());
        viewHolder.button.setTag(user);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getInstance().getMe().addFriend((User)v.getTag())) {
                    userAddAdapter.notifyDataSetChanged();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(UserAddAdapter.super.getContext())
                            .setTitle("Successful")//标题
                            .setMessage("Successful add friend")//内容
                            .create();
                    alertDialog1.show();
                    Message message = new Message();
                    JSONObject json = new JSONObject();
                    try{
                        json.put("status",800);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    message.obj = json;
                    AppData.getInstance().getFriendAddHandler().sendMessage(message);

                }else{
                    AlertDialog alertDialog1 = new AlertDialog.Builder(UserAddAdapter.super.getContext())
                            .setTitle("Warning")//标题
                            .setMessage("No one want to be your friend")//内容
                            .create();
                    alertDialog1.show();
                }
            }
        });
        viewHolder.image.setImageResource(user.getProfilePictureID());
        return view;
    }

    /**
     * The type View holder.
     */
//新增内部类ViewHolder，用于对控件的实例进行缓存。
    class ViewHolder{
        /**
         * The Layout.
         */
        LinearLayout layout;
        /**
         * The Name.
         */
        TextView name;
        /**
         * The Button.
         */
        Button button;
        /**
         * The Image.
         */
        ImageView image;
    }
}