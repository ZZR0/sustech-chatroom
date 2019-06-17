package com.example.se_project;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void testgetName1(){
        User u = new User("陈达寅",4.0);
        assertEquals("陈达寅",u.getName());
    }

    @Test
    public void testgetName2(){
        User u = new User("曾政然",4.0);
        assertEquals("曾政然",u.getName());
    }

    @Test
    public void testgetGpa1(){
        User u = new User("奥特曼",4.0);
        assertEquals((double)4.0, (double)u.getGpa(),0);
    }

    @Test
    public void testgetGpa2(){
        User u = new User("暴龙兽12138",3.62);
        assertEquals((double)3.62, (double)u.getGpa(),0);
    }

    @Test
    public void testUser1(){
        try {
            User u = new User("暴龙兽12138", 3.62);
            Class userClass = u.getClass();
            Field gpaField = userClass.getDeclaredField("gpa");
            gpaField.setAccessible(true);
            assertEquals(3.62,gpaField.get(u));
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUser2(){
        try {
            User u = new User("奥米加兽", 3.77);
            Class userClass = u.getClass();
            Field nameField = userClass.getDeclaredField("name");
            nameField.setAccessible(true);
            assertEquals("奥米加兽",nameField.get(u));
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFriend1(){
        User user1 = new User();
        User user2 = new User();
        user2.setId("abc");
        Assert.assertTrue(user1.addFriend(user2));
    }

    @Test
    public void testAddFriend2(){
        User user1 = new User();
        User user2 = new User();
        user2.setId("123");
        Assert.assertTrue(user1.addFriend(user2));
    }

    @Test
    public void testDelFriend1(){
        User user1 = new User();
        User user2 = new User();
        user2.setId("abc");
        user1.addFriend(user2);
        Assert.assertTrue(user1.deleteFriend(user2));
    }

    @Test
    public void testDelFriend2(){
        User user1 = new User();
        User user2 = new User();
        user2.setId("123");
        user1.addFriend(user2);
        Assert.assertTrue(user1.deleteFriend(user2));
    }

    @Test
    public void testSearchFriend1(){
        User user1 = new User();
        User user2 = new User();
        user2.setName("abc");
        user1.addFriend(user2);
        Assert.assertNotNull(user1.searchFriend("abc"));
    }

    @Test
    public void testSearchFriend2(){
        User user1 = new User();
        User user2 = new User();
        user2.setName("123");
        user1.addFriend(user2);
        Assert.assertEquals(0,user1.searchFriend("abc").size());
    }

}
