package sim;

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class ChatClientTest extends WebSocketClient {

    String id;
    String rid;

    public ChatClientTest(URI serverURI ) {
        super( serverURI );
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        JSONObject chatMsg = getChatMsg(id,"","");
        JSONObject jsonMsg = getJsonMsg(1, chatMsg, null);
        System.out.println("Send onOpen Msg: " + jsonMsg);
        send(jsonMsg.toString());
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage( String message ) {
        System.out.println(id + " received: " + message );
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public void heartBeatFrame() {
        sendPing();
    }

    public void sendMsg( String msg ){
        JSONObject chatMsg = getChatMsg(id,rid,msg);
        JSONObject jsonMsg = getJsonMsg(2, chatMsg, null);
        System.out.println("Send Msg: " + jsonMsg);
        send(jsonMsg.toString());
    }

    public JSONObject getJsonMsg(int action, JSONObject chatMsg, String extand){
        JSONObject json = new JSONObject();
        json.put("action",action);
        json.put("chatMsg",chatMsg);
        json.put("extand",extand);
        return json;
    }

    public JSONObject getChatMsg(String senderId, String receiverId, String msg) {

        JSONObject json = new JSONObject();
        json.put("msg",msg);
        json.put("receiverId",receiverId);
        json.put("senderId",senderId);
        return json;
    }

    public static void main( String[] args ) throws Exception {
        ChatClientTest c = new ChatClientTest( new URI( "ws://localhost:8088/ws" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.id = "1"; c.rid = "0";
        c.connect();
        ChatClientTest a = new ChatClientTest( new URI( "ws://localhost:8088/ws" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        a.id = "0"; a.rid = "1";
        a.connect();
        while(true){
            Thread.sleep(10000);
            c.heartBeatFrame();
            c.sendMsg("c to a");
            a.heartBeatFrame();
            a.sendMsg("a to c");
        }
    }

//    CHAT(2, "聊天消息"),
//    SIGNED(3, "消息签收"),

}