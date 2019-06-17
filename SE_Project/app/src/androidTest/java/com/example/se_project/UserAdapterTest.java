package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserAdapterTest {

    @Rule
    public ActivityTestRule<FriendActivity> rule = new ActivityTestRule<FriendActivity>(FriendActivity.class);

    @Test
    @UiThreadTest
    public void testUserGetViewLayer() {
        try {
            FriendActivity ac = rule.getActivity();
            User user = new User();
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAdapter userAdapter = new UserAdapter(ac, R.layout.friend_list_layout, l);
            UserAdapter.ViewHolder vh = (UserAdapter.ViewHolder) userAdapter.getView(0, null, null).getTag();
            vh.button.performClick();
            assertEquals(0, vh.layout.getVisibility());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testUserGetViewName() {
        try {
            FriendActivity ac = rule.getActivity();
            User user = new User();
            user.setName("123");
            user.setId("!@3");
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAdapter userAdapter = new UserAdapter(ac, R.layout.friend_list_layout, l);
            UserAdapter.ViewHolder vh = (UserAdapter.ViewHolder) userAdapter.getView(0, null, null).getTag();
            vh.button.performClick();
            assertEquals("123", vh.name.getText().toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}