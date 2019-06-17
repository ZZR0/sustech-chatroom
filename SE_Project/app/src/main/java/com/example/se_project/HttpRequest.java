package com.example.se_project;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is to deal with http request of json object ({@link JSONObject}).
 */
public final class HttpRequest {
    private HttpRequest(){}

    /**
     * To produce a {@link JSONObject} request by url and json.
     *
     * @param url_String the target url in string type.
     * @param json       the json object to send in {@link JSONObject} type.
     * @return the json object
     * @throws Exception the exception of {@link java.net.MalformedURLException},{@link IOException},{@link java.net.ProtocolException}
     */
    public static JSONObject jsonRequest(String url_String, JSONObject json) throws Exception{
        String result = "";
        URL url = new URL(url_String);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置参数类型是json格式
        connection.connect();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        writer.write(json.toString());
        writer.flush();
        writer.close();
        int HttpResult =connection.getResponseCode();
        if(HttpResult == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            result = br.readLine();
            br.close();
        }else{
            throw new Exception();
        }
        System.out.println(result);
        return JSONObject.parseObject(result);
    }

}

