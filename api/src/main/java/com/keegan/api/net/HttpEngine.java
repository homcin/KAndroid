package com.keegan.api.net;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http引擎处理类
 *
 * @author Keegan小钢
 * @date 15/6/21
 * @version 1.0
 */
public class HttpEngine {
    private static final String TAG = "HttpEngine";
    private static final String SERVER_URL = "http://uat.b.quancome.com/platform/api";
    private static final String REQUEST_METHOD = "POST";
    private static final String ENCODE_TYPE = "UTF-8";
    private static final int TIME_OUT = 20000;

    private static HttpEngine instance = null;

    private HttpEngine() {

    }

    public static HttpEngine getInstance() {
        if(instance == null) {
            instance = new HttpEngine();
        }
        return instance;
    }

    /**
     * @param typeOfT 响应的JSON数据经过gson解析为typeOfT类型对象实例
     */
    public<T> T postHandle(Map<String, String> paramsMap, Type typeOfT) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String data = joinParams(paramsMap);
        // 打印出请求
        Log.i(TAG, "request: " + data);
        HttpURLConnection connection = getConnection();
        connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
        connection.connect();
        //第一种是OutputStream，字节输出流，所以需要os.write(data.getBytes());
        //OutputStream os = connection.getOutputStream();
        //os.write(data.getBytes());
        //os.flush();
        //第二种用将OutputStream包装成PrintWriter，字符输出流，直接输出字符串
        out = new PrintWriter(connection.getOutputStream());
        out.print(data);
        out.flush();
        if(connection.getResponseCode() == 200) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                result += "\n" + line;
            }
            // 释放资源
            in.close();
            out.close();
            connection.disconnect();
            // 打印出结果
            Log.i(TAG, "response: " + result);
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        } else {
            connection.disconnect();
            return null;
        }
    }

    // 获取connection
    private HttpURLConnection getConnection() {
        HttpURLConnection connection = null;
        // 初始化connection
        try {
            // 根据地址创建URL对象
            URL url = new URL(SERVER_URL);
            // 根据URL对象打开链接
            connection = (HttpURLConnection)url.openConnection();
            // 设置请求的方式
            connection.setRequestMethod(REQUEST_METHOD);
            // 发送POST请求必须设置允许输入，默认为true
            connection.setDoInput(true);
            // 发送POST请求必须设置允许输出
            connection.setDoOutput(true);
            // 设置不使用缓存
            connection.setUseCaches(false);
            // 设置请求的超时时间
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            //设置通用的请求属性
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Response-Type", "json");
            connection.setChunkedStreamingMode(0);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 拼接参数列表: name1=value1&name2=value2的形式
    private String joinParams(Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : paramsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(paramsMap.get(key), ENCODE_TYPE));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
