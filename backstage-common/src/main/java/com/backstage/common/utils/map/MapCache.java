package com.backstage.common.utils.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * map 缓存
 *
 * @author yangfeng
 */
public class MapCache {

    public static ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap();

    public static void set(String key, Object value) {
        concurrentHashMap.put(key, value);
    }

    public static Object get(String key) {
        return concurrentHashMap.get(key);
    }

}
