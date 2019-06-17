package com.example.se_project;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * This adapter is used in list view in friend activity.
 */
public class UserAdapter extends ArrayAdapter<User>{

    private final int resourceId;
    private List<User> objects;
    private UserAdapter userAdapter=this;

    /**
     * Instantiates a new User adapter.
     *
     * @param context            the context
     * @param userViewResourceId the user view resource id
     * @param objects            the objects
     */
    public UserAdapter(Context context, int userViewResourceId,
                       List<User> objects){

        super(context, userViewResourceId, objects);
        resourceId = userViewResourceId;
        this.objects=objects;
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
            viewHolder.image = (ImageView) view.findViewById(R.id.user_image);
            viewHolder.button=(Button) view.findViewById(R.id.delete_request);
            view.setTag(viewHolder);

        } else {
            //convertView参数用于将之前加载好的布局进行缓存，以便之后可以进行复用（提高效率）
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /*display the user*/
        viewHolder.layout.setVisibility(View.VISIBLE);
        viewHolder.name.setText(user.getName());
        viewHolder.image.setImageResource(user.getProfilePictureID());
        viewHolder.button.setTag(user);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppData.getInstance().getMe().deleteFriend((User)v.getTag())){
                    userAdapter.notifyDataSetChanged();
                    AlertDialog alertDialog1 = new AlertDialog.Builder(UserAdapter.super.getContext())
                            .setTitle("Good News")//标题
                            .setMessage("Delete success")//内容
                            .create();
                    alertDialog1.show();
                }else{
                    AlertDialog alertDialog1 = new AlertDialog.Builder(UserAdapter.super.getContext())
                            .setTitle("Oops")//标题
                            .setMessage("You can't delete this friend")//内容
                            .create();
                    alertDialog1.show();
                }
            }
        });
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
         * The Image.
         */
        ImageView image;
        /**
         * The Button.
         */
        Button button;
    }
}