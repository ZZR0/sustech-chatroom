package com.example.se_project;

import android.support.test.annotation.UiThreadTest;

import com.example.se_project.Chat.WSClient;

import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;

public class WSClientTest {

    @Test
    @UiThreadTest
    public void testOnOpen(){
        try{
            URI Uri = new URI("ws://10.21.72.100:8088/ws");
            WSClient wsClient = new WSClient(Uri);
            ServerHandshake serverHandshake = PowerMockito.mock(ServerHandshake.class);
            wsClient.connect();
            wsClient.onOpen(serverHandshake);

            Method startHeartBeat = WSClient.class.getDeclaredMethod("startHeartBeat");
            PowerMockito.verifyPrivate(startHeartBeat);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnMessage(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.onMessage("1");
            Method println = System.out.getClass().getDeclaredMethod("println");
            PowerMockito.verifyPrivate(println);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnClose(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.onClose(1,"123",true);
            Method s2 = System.out.getClass().getDeclaredMethod("println");
            PowerMockito.verifyPrivate(wsClient).invoke(s2);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testOnError(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            Exception ex = new Exception();
            wsClient.onError(ex);
            Method s3 = Exception.class.getMethod("printStackTrace");
            PowerMockito.verifyPrivate(wsClient).invoke(s3);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testSendMsg1(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.sendMsg("abc","123");
            Method s4 = System.out.getClass().getMethod("println");
            PowerMockito.verifyPrivate(wsClient).invoke(s4);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testSendMsg2(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.sendMsg("abc","123");
            Method s5 = WSClient.class.getDeclaredMethod("checkConnection");
            PowerMockito.verifyPrivate(wsClient).invoke(s5);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testSignMsg1(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.signMsg(new ArrayList<String>());
            Method s6 = System.out.getClass().getMethod("println");
            PowerMockito.verifyPrivate(wsClient).invoke(s6);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @UiThreadTest
    public void testSignMsg2(){
        try{
            WSClient wsClient = PowerMockito.mock(WSClient.class);
            wsClient.signMsg(new ArrayList<String>());
            Method s7 = WSClient.class.getDeclaredMethod("checkConnection");
            PowerMockito.verifyPrivate(wsClient).invoke(s7);
        }catch(java.lang.Exception e){
            e.printStackTrace();
        }
    }
}
