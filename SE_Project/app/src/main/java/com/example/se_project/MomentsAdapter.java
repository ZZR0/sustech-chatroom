package com.example.se_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * This is adapt to list view in moments activity.
 */
public class MomentsAdapter extends ArrayAdapter<Moments> implements Serializable {

    private final int resourceId;
    private MomentsAdapter adapter=this;

    /**
     * Instantiates a new Moments adapter.
     *
     * @param context            the context
     * @param textViewResourceId the text view resource id
     * @param objects            the objects
     */
    public MomentsAdapter(Context context, int textViewResourceId,
                          List<Moments> objects){
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
        final Moments moments = getItem(position);
        View view;
        ViewHolder viewHolder;

        /*加载自定义布局与控件实例*/
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            //创建控件实例并进行缓存
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.moments_text);
            viewHolder.button=(Button) view.findViewById(R.id.good);
            viewHolder.image=(ImageView) view.findViewById(R.id.moments_profile_picture) ;
            viewHolder.goodNum=(TextView)view.findViewById(R.id.good_num) ;
            view.setTag(viewHolder);
        } else {
            //convertView参数用于将之前加载好的布局进行缓存，以便之后可以进行复用（提高效率）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /*接受与发送消息的分类处理*/
        //如果为收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
        viewHolder.button.setTag(moments);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moments != null)
                    AppData.getInstance().getMe().thumbUpMoment(moments.getMomentId());
                moments.addGood();
                adapter.notifyDataSetChanged();
            }
        });
        viewHolder.goodNum.setText(String.valueOf(moments.getGoodNum()));
        viewHolder.text.setText(moments.getText());
        viewHolder.image.setImageResource(moments.getUser().getProfilePictureID());
        return view;
    }

    /**
     * The type View holder.
     */
//新增内部类ViewHolder，用于对控件的实例进行缓存。
    class ViewHolder implements Serializable{
        /**
         * The Text.
         */
        TextView text;
        /**
         * The Button.
         */
        Button button;
        /**
         * The Image.
         */
        ImageView image;
        /**
         * The Good num.
         */
        TextView goodNum;
    }
}