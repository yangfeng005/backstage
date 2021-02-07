package com.backstage.common.utils.json;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    /**
     * string字符串是不是json格式
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
