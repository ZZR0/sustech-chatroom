package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MomentsAdapterTest {

    @Rule
    public ActivityTestRule<MomentsActivity> rule = new ActivityTestRule<MomentsActivity>(MomentsActivity.class);

    @Test
    @UiThreadTest
    public void testMsgGetView1(){
        try {
            MomentsActivity ac = rule.getActivity();
            User user = new User();
            String string = "abc";
            int good = 0;
            Moments moments = new Moments(user,string,good);
            List<Moments> l = new ArrayList<Moments>();
            l.add(moments);
            MomentsAdapter momentsAdapter = new MomentsAdapter(ac,R.layout.moments_layout,l);
            MomentsAdapter.ViewHolder vh = (MomentsAdapter.ViewHolder)momentsAdapter.getView(0,null,null).getTag();
            assertEquals(0,Integer.parseInt(vh.goodNum.getText().toString()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testMsgGetView2(){
        try {
            MomentsActivity ac = rule.getActivity();
            User user = new User();
            String string = "abc";
            int good = 0;
            Moments moments = new Moments(user,string,good);
            List<Moments> l = new ArrayList<Moments>();
            l.add(moments);
            MomentsAdapter momentsAdapter = new MomentsAdapter(ac,R.layout.moments_layout,l);
            MomentsAdapter.ViewHolder vh = (MomentsAdapter.ViewHolder)momentsAdapter.getView(0,null,null).getTag();
            assertEquals(moments.getText(),vh.text.getText().toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
