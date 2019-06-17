package com.example.se_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.se_project.Chat.ImageMsg;

import java.util.List;

/**
 * Created by cpj on 2016/3/15.
 * This is adapted to list view in chat activity
 */
public class MsgAdapter extends ArrayAdapter<Msg>{

    private int resourceId;
    private User chatUser;

    /**
     * Instantiates a new Msg adapter.
     *
     * @param context            the context
     * @param textViewResourceId the text view resource id
     * @param objects            the objects
     * @param chatUser           the chat user
     */
    public MsgAdapter(Context context, int textViewResourceId,
                      List<Msg> objects,User chatUser){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.chatUser=chatUser;
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
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;

        /*加载自定义布局与控件实例*/
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            //创建控件实例并进行缓存
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_msg_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_msg_layout);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
            viewHolder.leftImageMsg=(ImageView) view.findViewById(R.id.left_image_msg);
            viewHolder.rightImageMsg=(ImageView) view.findViewById(R.id.right_image_msg);
            viewHolder.leftImage=(ImageView) view.findViewById(R.id.left_chat_user_image);
            viewHolder.rightImage=(ImageView) view.findViewById(R.id.right_chat_user_image);
            view.setTag(viewHolder);

        } else {
            //convertView参数用于将之前加载好的布局进行缓存，以便之后可以进行复用（提高效率）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /*接受与发送消息的分类处理*/
        //如果为收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
        if(msg.getType() == Msg.TYPE_RECEIVED){
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);

            if (msg.getClass().equals(ImageMsg.class)) {
//                viewHolder.leftMsg.setVisibility(View.GONE);
                String url = ((ImageMsg) msg).getBigImage();
                int width = Utils.getScreenWidth();
                viewHolder.leftImageMsg.setMaxWidth(width/2);
                viewHolder.leftImageMsg.setMaxHeight(width/2*5);

                viewHolder.leftMsg.setText("");
                Glide.with(AppData.getInstance().getChatContext())
                        .load(url)
                        .into(viewHolder.leftImageMsg);

            }else {
//                viewHolder.leftImageMsg.setVisibility(View.GONE);
                viewHolder.leftImageMsg.setImageBitmap(null);
                viewHolder.leftMsg.setText(msg.getContent());
            }

            viewHolder.leftImage.setImageResource(chatUser.getProfilePictureID());

        } else if(msg.getType() == Msg.TYPE_SENT){
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);


            if (msg.getClass().equals(ImageMsg.class)) {
//                viewHolder.rightMsg.setVisibility(View.GONE);
                String url = ((ImageMsg) msg).getBigImage();
                int width = Utils.getScreenWidth();
                viewHolder.rightImageMsg.setMaxWidth(width/2);
                viewHolder.rightImageMsg.setMaxHeight(width/2*5);

                viewHolder.rightMsg.setText("");
                Glide.with(AppData.getInstance().getChatContext())
                        .load(url)
                        .into(viewHolder.rightImageMsg);
            }else {
//                viewHolder.rightImageMsg.setVisibility(View.GONE);
                viewHolder.rightImageMsg.setImageBitmap(null);
                viewHolder.rightMsg.setText(msg.getContent());
            }

            viewHolder.leftImage.setImageResource(AppData.getInstance().getMe().getProfilePictureID());
        }
        return view;
    }

    /**
     * The type View holder.
     */
//新增内部类ViewHolder，用于对控件的实例进行缓存。
    class ViewHolder{
        /**
         * The Left layout.
         */
        LinearLayout leftLayout;
        /**
         * The Right layout.
         */
        LinearLayout rightLayout;
        /**
         * The Left msg.
         */
        TextView leftMsg;
        /**
         * The Right msg.
         */
        TextView rightMsg;
        /**
         * The Left image msg.
         */
        ImageView leftImageMsg;
        /**
         * The Right image msg.
         */
        ImageView rightImageMsg;
        /**
         * The Left image.
         */
        ImageView leftImage;
        /**
         * The Right image.
         */
        ImageView rightImage;
    }
}