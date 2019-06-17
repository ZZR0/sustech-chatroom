package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserAddAdapterTest {

    @Rule
    public ActivityTestRule<FriendActivity> rule = new ActivityTestRule<FriendActivity>(FriendActivity.class);

    @Test
    @UiThreadTest
    public void testUserAddGetViewLayer(){
        try {
            FriendActivity ac = rule.getActivity();
            User user = new User();
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAddAdapter userAddAdapter = new UserAddAdapter(ac,R.layout.friend_add_layout, l);
            UserAddAdapter.ViewHolder vh = (UserAddAdapter.ViewHolder)userAddAdapter.getView(0,null,null).getTag();
            vh.button.performClick();
            assertEquals(0,vh.layout.getVisibility());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testUserAddGetViewName(){
        try {
            FriendActivity ac = rule.getActivity();
            User user = new User();
            user.setName("123");
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAddAdapter userAddAdapter = new UserAddAdapter(ac,R.layout.friend_add_layout, l);
            UserAddAdapter.ViewHolder vh = (UserAddAdapter.ViewHolder)userAddAdapter.getView(0,null,null).getTag();
            vh.button.performClick();
            assertEquals("123",vh.name.getText().toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
