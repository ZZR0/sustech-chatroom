package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MsgAdapterTest {

    @Rule
    public ActivityTestRule<FriendActivity> rule = new ActivityTestRule<FriendActivity>(FriendActivity.class);

    @Test
    @UiThreadTest
    public void testMsgGetViewReceived(){
        try {
            FriendActivity ac = rule.getActivity();
            Msg msg = new Msg("123",0);
            List<Msg> l = new ArrayList<Msg>();
            User user = new User("abc",4.0);
            l.add(msg);
            MsgAdapter msgAdapter = new MsgAdapter(ac, R.layout.msg_layout, l,user);
            MsgAdapter.ViewHolder vh = (MsgAdapter.ViewHolder)msgAdapter.getView(0,null,null).getTag();
            assertEquals(0,vh.leftLayout.getVisibility());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testMsgGetViewSend(){
        try {
            FriendActivity ac = rule.getActivity();
            Msg msg = new Msg("132",1);
            List<Msg> l = new ArrayList<Msg>();
            User user = new User("abc",4.0);
            l.add(msg);
            MsgAdapter msgAdapter = new MsgAdapter(ac, R.layout.msg_layout, l, user);
            MsgAdapter.ViewHolder vh = (MsgAdapter.ViewHolder)msgAdapter.getView(0,null,null).getTag();
            assertEquals(0,vh.rightLayout.getVisibility());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
