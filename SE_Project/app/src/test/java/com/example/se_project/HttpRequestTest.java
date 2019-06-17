package com.example.se_project;

import org.junit.Assert;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;


public class HttpRequestTest {

    @Test
    public void jsonRequestWithWrongInfo() {
            JSONObject json_data = new JSONObject();
            String url = "http://10.21.72.100:8081/" + "/login";
            try{
                json_data.put("username", "fjoashfo");
                json_data.put("password", "4sda64d6af46sa4f");
                System.out.println(json_data.toJSONString());
                System.out.println(json_data.toString());
                JSONObject jsonObject = HttpRequest.jsonRequest(url,json_data);
                Assert.assertEquals(500,(int)jsonObject.get("status"));
            }catch (Exception e){
                System.err.println(e.toString());
            }
    }


    @Test
    public void jsonRequestWithCorrectInfo() {
        JSONObject json_data = new JSONObject();
        String url = "http://10.21.72.100:8081/" + "/login";
        try{
            json_data.put("username", "test");
            json_data.put("password", "123456");
            JSONObject jsonObject = HttpRequest.jsonRequest(url,json_data);
            Assert.assertEquals("OK",jsonObject.get("msg"));
        }catch (Exception e){
            System.err.println("JSON error.");
        }
    }

    @Test
    public void translateEN2ZH() {
        JSONObject json_data = new JSONObject();
        String url = "http://47.107.254.208:5000" + "/en2zh";
        try{
            json_data.put("sentence", "HI.");
            System.out.println(json_data.toJSONString());
            System.out.println(json_data.toString());
            JSONObject jsonObject = HttpRequest.jsonRequest(url,json_data);
            System.out.println(jsonObject.toString());
            Assert.assertEquals(200,(int)jsonObject.get("status"));
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }



}