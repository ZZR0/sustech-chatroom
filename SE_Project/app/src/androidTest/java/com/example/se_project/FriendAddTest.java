package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FriendAddTest {

    @Rule
    public ActivityTestRule<FriendAddActivity> rule = new ActivityTestRule<FriendAddActivity>(FriendAddActivity.class);

    @Test
    @UiThreadTest
    public void testOnBackPressedNoExist() {
        try {
            FriendAddActivity ac = rule.getActivity();
            String inputext = "abc";
            Field field = FriendAddActivity.class.getDeclaredField("inputText");
            field.setAccessible(true);
            field.set(ac,inputext);
            field = FriendAddActivity.class.getDeclaredField("search");
            field.setAccessible(true);
            Button search = (Button) field.get(ac);
            search.performClick();
            assertNotNull(ac.alertdialog1.getContext());
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Test
    @UiThreadTest
    public void testOnBackPressedExist() {
        try {
            FriendAddActivity ac = rule.getActivity();
            String inputext = "d";
            Field field = FriendAddActivity.class.getDeclaredField("inputText");
            field.setAccessible(true);
            field.set(ac,inputext);
            field = FriendAddActivity.class.getDeclaredField("search");
            field.setAccessible(true);
            Button search = (Button) field.get(ac);
            search.performClick();
            assertNull(ac.alertdialog1.getContext());
        } catch (IllegalArgumentException | NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}