package com.backstage.common.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

/**
 * http工具类
 *
 * @author yangfeng
 */
public class HttpUtil {

    /**
     * http请求
     *
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    public static String httpRequest(String requestUrl, String requestMethod) throws Exception {
        String result = "";
        BufferedReader in = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        URLConnection connection = url.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();

        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        connection.setConnectTimeout(30000);//毫秒
        connection.setReadTimeout(30000);
        return result;
    }
}
