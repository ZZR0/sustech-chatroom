package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
public class MomentsActivityTest {
    @Rule
    public ActivityTestRule<MomentsActivity> rule = new ActivityTestRule<MomentsActivity>(MomentsActivity.class);

    @Test
    @UiThreadTest
    public void testOnBackPressed1() {
        MomentsActivity momentsActivity = rule.getActivity();
        momentsActivity.onBackPressed();
        Assert.assertTrue(momentsActivity.isFinishing());
    }

    @Test
    @UiThreadTest
    public void testOnBackPressed2() {
        MomentsActivity momentsActivity = rule.getActivity();
        momentsActivity.onBackPressed();
        momentsActivity.onResume();
        Assert.assertFalse(momentsActivity.isDestroyed());
    }
}
