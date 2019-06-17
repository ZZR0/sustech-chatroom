package com.example.se_project;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UploadActivityTest {

    @Rule
    public ActivityTestRule<UploadActivity> rule = new ActivityTestRule<UploadActivity>(UploadActivity.class);

    @Test
    @UiThreadTest
    public void testOnClick1(){
        try{
            UploadActivity uploadActivity = new UploadActivity();
            View view = new View(uploadActivity);
            view.setId(R.id.choose_image);
            uploadActivity.onClick(view);
            Method loadImage = UploadActivity.class.getDeclaredMethod("loadImage");
            PowerMockito.verifyPrivate(loadImage);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnClick2(){
        try{
            UploadActivity uploadActivity = PowerMockito.mock(UploadActivity.class);
            View view = new View(uploadActivity);
            view.setId(R.id.upload_image);
            uploadActivity.onClick(view);
            Method uploadImage = UploadActivity.class.getDeclaredMethod("uploadImage");
            PowerMockito.verifyPrivate(uploadImage);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnRequestPermissionsResult(){
        UploadActivity uploadActivity = rule.getActivity();
        String[] s = new String[1];
        int[] i = new int[1];
        uploadActivity.onRequestPermissionsResult(0,s,i);
        assertTrue(uploadActivity.isGetPermission());
    }

    @Test
    @UiThreadTest
    public void testOnPermissionGranted(){
        UploadActivity uploadActivity = rule.getActivity();
        List<String> l = new ArrayList<String>();
        uploadActivity.onPermissionsGranted(0,l);
        assertTrue(uploadActivity.isPermissionGranted());
    }

    @Test
    @UiThreadTest
    public void testOnPermissionDenied(){
        UploadActivity uploadActivity = rule.getActivity();
        List<String> l = new ArrayList<String>();
        uploadActivity.onPermissionsDenied(0,l);
        assertTrue(uploadActivity.isPermissionDenied());
    }
}
