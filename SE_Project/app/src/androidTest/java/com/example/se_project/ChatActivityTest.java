package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class ChatActivityTest {

    @Rule
    public ActivityTestRule<ChatActivity> rule = new ActivityTestRule<ChatActivity>(ChatActivity.class);


    @Test
    @UiThreadTest
    public void testOnBackPressedRtn(){
        ChatActivity ac = (ChatActivity)rule.getActivity();
        ac.onBackPressed();
        Assert.assertTrue(ac.isFinishing());
    }


    @Test
    @UiThreadTest
    public void testOnBackPressedFlags(){
        ChatActivity ac = (ChatActivity)rule.getActivity();
        ac.onBackPressed();
        Assert.assertEquals(0,rule.getActivity().getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}