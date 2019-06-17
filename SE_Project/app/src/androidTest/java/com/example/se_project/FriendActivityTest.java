package com.example.se_project;

import android.app.Activity;
import android.content.Intent;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ArrayAdapter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FriendActivityTest {
    @Rule
    public ActivityTestRule<FriendActivity> rule = new ActivityTestRule <FriendActivity>(FriendActivity.class);

    @Test
    @UiThreadTest
    public void testOnRefreshView(){
        try{
            FriendActivity ac = rule.getActivity();
            User user = new User();
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAdapter userAdapter = new UserAdapter(ac,R.layout.friend_list_layout,l);
            Field field = FriendActivity.class.getDeclaredField("adapter");
            field.setAccessible(true);
            field.set(ac,userAdapter);
            ac.refreshView();
            Method ms = ArrayAdapter.class.getDeclaredMethod("notifyDataSetChanged");
            PowerMockito.verifyPrivate(ac).invoke(ms);
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testOnRefreshView2(){
        try{
            FriendActivity ac = rule.getActivity();
            User user = new User();
            List<User> l = new ArrayList<User>();
            l.add(user);
            UserAdapter userAdapter = new UserAdapter(ac,R.layout.friend_list_layout,l);
            Field field = FriendActivity.class.getDeclaredField("adapter");
            field.setAccessible(true);
            field.set(ac,userAdapter);
            ac.refreshView();
            ArrayAdapter adapter = (ArrayAdapter) field.get(ac);
            Field field1 = ArrayAdapter.class.getDeclaredField("mNotifyOnChange");
            Assert.assertTrue((Boolean) field1.get(adapter));
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnClick1(){
        try{
            FriendActivity ac = rule.getActivity();
            View view = new View(ac);
            view.setId(R.id.search_friendlist);
            ac.onClick(view);
            Method ms = FriendActivity.class.getDeclaredMethod("searchFriendByName", String.class);
            PowerMockito.verifyPrivate(ac).invoke(ms);
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnClick2(){
        try{
            FriendActivity ac = rule.getActivity();
            View view = new View(ac);
            view.setId(R.id.moments);
            ac.onClick(view);
            Method ms = Activity.class.getDeclaredMethod("startActivity", Intent.class);
            PowerMockito.verifyPrivate(ac).invoke(ms);
        }catch (java.lang.Exception e){
            e.printStackTrace();
        }
    }
}
