package com.example.se_project;

import com.example.se_project.Chat.ChatHistory;
import com.example.se_project.Chat.WSClient;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AppDataTest {
    @Test
    public void testOnSendChatMsg1() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            URI Uri = new URI("ws://10.21.72.100:8088/ws");
            List<String> ls = new ArrayList<String>();
            WSClient wsClient = new WSClient(Uri);
            wsClient.signMsg(ls);
            wsClient.connect();
            appData.setChattingFriend(user);
            List<Msg> l = new ArrayList<Msg>();
            ChatHistory chatHistory = new ChatHistory();
            chatHistory.setMsgList(l);

            appData.getChatHistory().put(appData.getChattingFriend().getId(),chatHistory);
            appData.setWsClient(wsClient);
            appData.sendChatMsg("123");
            assertEquals("123",appData.getChatHistory()
                                         .get(user.getId())
                                         .getMsgList()
                                         .get(0)
                                         .getContent());
        }catch(IllegalAccessException | URISyntaxException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testOnSendChatMsg2() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            URI Uri = new URI("ws://10.21.72.100:8088/ws");
            List<String> ls = new ArrayList<String>();
            WSClient wsClient = new WSClient(Uri);
            wsClient.signMsg(ls);
            wsClient.connect();
            appData.setChattingFriend(user);
            List<Msg> l = new ArrayList<Msg>();
            ChatHistory chatHistory = new ChatHistory();
            chatHistory.setMsgList(l);
            appData.getChatHistory().put(appData.getChattingFriend().getId(),chatHistory);
            appData.setWsClient(wsClient);
            appData.sendChatMsg("456");
            assertNotEquals("123",appData.getChatHistory()
                    .get(user.getId())
                    .getMsgList()
                    .get(0)
                    .getContent());
        }catch(IllegalAccessException | URISyntaxException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testOnReceiveChatMsgA1() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            appData.setChattingFriend(user);
            appData.reciveChatMsg(user.getId(),"123","0");
            assertEquals("123",appData.getChatHistory()
                    .get(user.getId())
                    .getMsgList()
                    .get(0)
                    .getContent());
        }catch(IllegalAccessException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testOnReceiveChatMsgA2() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            appData.setChattingFriend(user);
            appData.reciveChatMsg(user.getId(),"456","0");
            assertNotEquals("123",appData.getChatHistory()
                    .get(user.getId())
                    .getMsgList()
                    .get(0)
                    .getContent());
        }catch(IllegalAccessException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testOnReceiveChatMsgB1() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            appData.setChattingFriend(user);
            appData.reciveChatMsg(user.getId(),"123","0",new Date(System.currentTimeMillis()));
            assertEquals("123",appData.getChatHistory()
                    .get(user.getId())
                    .getMsgList()
                    .get(0)
                    .getContent());
        }catch(IllegalAccessException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testOnReceiveChatMsgB2() {
        try{
            Class<?> clazz = AppData.class;// 获取PrivateClass整个类
            AppData appData = (AppData) clazz.newInstance();
            User user = new User("abc",4.0);
            user.setId("abc");
            appData.setChattingFriend(user);
            appData.reciveChatMsg(user.getId(),"123","0",new Date(System.currentTimeMillis()));
            sleep(500);
            Date newTime = new Date(System.currentTimeMillis());
            assertNotEquals(newTime,appData.getChatHistory()
                    .get(user.getId())
                    .getMsgList()
                    .get(0)
                    .getSendTime());
        }catch(IllegalAccessException | InterruptedException | NullPointerException | InstantiationException e){
            e.printStackTrace();
        }
    }

}
