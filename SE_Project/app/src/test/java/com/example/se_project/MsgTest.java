package com.example.se_project;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MsgTest {
    @Test
    public void testMsg1(){
        try {
            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
            Msg msg = (Msg) clazz.newInstance();// 创建一个实例
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            assertEquals(0,fs[0].get(msg));
            assertEquals(1,fs[1].get(msg));
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

//    @Test
//    public void testMsg2(){
//        try {
//            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
//            Msg msg = (Msg) clazz.newInstance();// 创建一个实例
//            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
//            fs[2].setAccessible(true);
//            fs[3].setAccessible(true);
//            assertEquals(null,fs[2].get(msg));
//            assertEquals(0,fs[3].get(msg));
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testsetContent1(){
//        try {
//            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
//            Msg msg = (Msg) clazz.newInstance();// 创建一个实例
//            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
//            fs[2].setAccessible(true);
//            assertEquals(null,fs[2].get(msg));
//            msg.setContent("test");
//            assertEquals("test",fs[2].get(msg));
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testsetContent2(){
//        try {
//            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
//            Msg msg = (Msg) clazz.newInstance();// 创建一个实例
//            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
//            fs[2].setAccessible(true);
//            assertEquals(null,fs[2].get(msg));
//            msg.setContent("test123");
//            assertEquals("test123",fs[2].get(msg));
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testsetType1(){
//        try {
//            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
//            Msg msg = (Msg) clazz.newInstance();// 创建一个实例
//            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
//            fs[3].setAccessible(true);
//            assertEquals(0,fs[3].get(msg));
//            msg.setType(1);
//            assertEquals(1,fs[3].get(msg));
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testgetContent1(){
        try {
            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
            Msg msg = new Msg("test",1);
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            fs[3].setAccessible(true);
            assertEquals("test",msg.getContent());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetContent2(){
        try {
            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
            Msg msg = new Msg("test123",1);
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            fs[3].setAccessible(true);
            assertEquals("test123",msg.getContent());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetType1(){
        try {
            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
            Msg msg = new Msg("test",1);
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            fs[3].setAccessible(true);
            assertEquals(1,msg.getType());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetType2(){
        try {
            Class<?> clazz = Msg.class;// 获取PrivateClass整个类
            Msg msg = new Msg("test",0);
            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            fs[3].setAccessible(true);
            assertEquals(0,msg.getType());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }





}
