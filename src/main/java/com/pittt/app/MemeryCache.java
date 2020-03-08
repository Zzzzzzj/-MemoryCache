package com.pittt.app;


import java.util.HashMap;

public enum MemeryCache {

    INSTANCE;

    // 缓存内容列表,HashMap实现
    HashMap<String, Object> l;
    // 缓存过期时间,HashMap实现
    HashMap<String, Long> t;

    // 初始化
    MemeryCache() {
        // 初始容量均定为100
        l = new HashMap<String, Object>(100);
        t = new HashMap<String, Long>(100);
    }

    // 写入缓存内容,不考虑存在直接覆写
    public boolean add(String key, Object value) {
        l.put(key, value);
        return true;
    }

    // 写入缓存内容,带过期时间
    public boolean add(String key, Object value, Long timeOut) {
        l.put(key, value);
        t.put(key, timeOut);
        return true;
    }

    // 读取缓存内容
    public Object get(String key) {
        if (!checkTimeOut(key)) {
            return l.get(key);
        }
        return null;
    }

    // 读取缓存过期时间
    public Long getTimeOut(String key) {
        if (!checkTimeOut(key)) {
            return t.get(key);
        }
        return -1L;
    }

    // 检查key是否存在
    public boolean checkExist(String key) {
        return l.containsKey(key);
    }

    // 检查key是否有过期时间
    public boolean checkExistTimeOut(String key) {
        return t.containsKey(key);
    }

    // 检查key是否过期
    public boolean checkTimeOut(String key) {
        return false;
    }

    // 删除缓存内容
    public boolean removeKey(String key) {
        l.remove(key);
        return true;
    }

    // 删除过期时间
    public boolean removeTimeOut(String key) {
        return true;
    }


}
