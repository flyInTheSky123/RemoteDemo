package com.person.remote;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;

//import net.sf.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

//远程调用接口 ，解析json数据
// 1，将获取的json对象进行转换为String，再通过hutool包中的JsonObject 对象的get方式获取参数
// 2， 使用jackson 包中的 ObjectMapper(inputStream,Entry.class) 对实体类进行自动注入数据
public class Remote {
    public static void main(String[] args) {


        Remote remote = new Remote();

        JSONObject jsonObject = new JSONObject();

//        https://jiaotong.baidu.com/trafficindex/city/roadrank?cityCode=307&roadtype=

        String result = remote.remote("http://t.weather.sojson.com/api/weather/city/101040100", 5000, jsonObject, "GET", "UTF-8");



     /*
        JSONObject re = new JSONObject(result);
        JSONObject data = (JSONObject) re.get("message");
        Object status = (Object) re.get("status");

        System.out.println("status:" + status);

        JSONArray list = data.getJSONArray("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject jobject = list.getJSONObject(i);
            System.out.println("cityCode:" + jobject.get("citycode"));
        }

*/
        // System.out.println(re.getJSONObject("list"));

        System.out.println("result:" + result);


    }

    //远程调用接口
    public String remote(String url, int timeOut, JSONObject object, String method, String contentType) {
        URL conn;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            conn = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) conn.openConnection();
            //设置参数
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setRequestMethod(method);
            urlConnection.setRequestProperty("Content-Type", contentType);
            urlConnection.setDoOutput(true);

            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
            //  outputStreamWriter.write(object.toString());
            // System.out.println("object.toString():"+object.toString());
            // outputStreamWriter.flush();

            InputStream inputStream = urlConnection.getInputStream();

//            一种简便的方式
            //  new ObjectMapper(inputStream,Stu.class);

            //-------------------
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;

            if ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // outputStreamWriter.close();
            inputStreamReader.close();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        return stringBuilder.toString();
    }
}
