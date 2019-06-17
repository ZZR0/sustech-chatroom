package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> rule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);


    @Test
    @UiThreadTest
    public void onClickSuccess() {
        RegisterActivity registerActivity = rule.getActivity();
        Button ensure;
        TextView register_username, register_password, register_repassword, register_GPA;
        register_username = registerActivity.findViewById(R.id.username);
        register_password = registerActivity.findViewById(R.id.password);
        register_repassword = registerActivity.findViewById(R.id.password_ensure);
        register_GPA = registerActivity.findViewById(R.id.gpa);
        ensure = registerActivity.findViewById(R.id.ensure_button);
        register_username.setText("matt");
        register_password.setText("123456");
        register_repassword.setText("123456");
        register_GPA.setText("4.0");
        ensure.performClick();
        Assert.assertTrue(registerActivity.alertdialog1 == null || !registerActivity.alertdialog1.isShowing());
    }

    @Test
    @UiThreadTest
    public void onClickFail() {
        RegisterActivity registerActivity = rule.getActivity();
        Button ensure;
        TextView register_username, register_password, register_repassword, register_GPA;
        register_username = registerActivity.findViewById(R.id.username);
        register_password = registerActivity.findViewById(R.id.password);
        register_repassword = registerActivity.findViewById(R.id.password_ensure);
        register_GPA = registerActivity.findViewById(R.id.gpa);
        ensure = registerActivity.findViewById(R.id.ensure_button);
        register_username.setText("matt");
        register_password.setText("123456");
        register_repassword.setText("1234567");
        register_GPA.setText("4.0");
        ensure.performClick();
        Assert.assertTrue(registerActivity.alertdialog1.isShowing());
    }


}