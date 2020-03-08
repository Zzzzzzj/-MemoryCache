package com.pittt.app;

import lombok.extern.java.Log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

/**
 * MemoryCache Enum
 *
 * @author wuzhijie
 * @date 2020/03/08
 */

@Log
public enum MemoryCache {

    /**
     * 唯一实例
     */
    INSTANCE;

    /**
     * 缓存内容列表,HashMap实现
     */
    private HashMap<String, Object> l;
    /**
     * 缓存过期时间,HashMap实现
     */
    private HashMap<String, Long> t;

    /**
     * 初始化
     */
    MemoryCache() {
        // 初始容量均定为100
        l = new HashMap<String, Object>(100);
        t = new HashMap<String, Long>(100);
    }

    /**
     * 写入缓存内容,不考虑存在直接覆写.如果key存在过期时间,则清除其过期时间
     *
     * @param key   缓存key
     * @param value 缓存内容
     */
    public void set(String key, Object value) {
        l.put(key, value);
        removeTimeOut(key);
    }

    /**
     * 写入缓存内容,带过期时间(毫秒数)
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeOut 缓存过期时间(毫秒)
     */
    public void set(String key, Object value, Long timeOut) {
        long now = System.currentTimeMillis();
        l.put(key, value);
        t.put(key, now + timeOut);
    }

    /**
     * 读取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容 或 null(过期或不存在)
     */
    public Object get(String key) {
        // 未过期返回获取值
        if (!checkTimeOut(key)) {
            return l.get(key);
        }
        return null;
    }

    /**
     * 读取缓存过期时间
     *
     * @param key 缓存key
     * @return
     */
    public LocalDateTime getTimeOut(String key) {
        if (!checkTimeOut(key)) {
            Long timestamp = t.get(key);
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }
        return null;
    }

    /**
     * 获取当前缓存总数量
     *
     * @return 当前缓存总数量
     */
    public int getCachedCount() {
        return l.size();
    }

    /**
     * 检查key是否存在
     *
     * @param key 缓存key
     * @return true:存在
     * false:不存在
     */
    public boolean checkExist(String key) {
        return l.containsKey(key);
    }

    /**
     * 检查key是否有过期时间
     *
     * @param key 缓存key
     * @return true:存在
     * false:不存在
     */
    public boolean checkExistTimeOut(String key) {
        return t.containsKey(key);
    }

    /**
     * 检查key是否过期,对不存在key同样返回未过期
     *
     * @param key 缓存key
     * @return true:过期
     * false:未过期
     */
    public boolean checkTimeOut(String key) {
        // 检查对应key是否存在且是否存在过期时间
        if (checkExist(key)) {
            if (checkExistTimeOut(key)) {
                Long now = System.currentTimeMillis();
                // 对比当前时间与过期时间,如果过期返回true
                // (不在此清除过期缓存内容和过期时间,放到定时任务里去)
                boolean timeOut = now > t.get(key) ? true : false;
                return timeOut;
            }
            return false;
        }
        return false;
    }

    /**
     * 删除缓存内容
     *
     * @param key 缓存key
     * @return true:删除成功
     */
    public boolean removeKey(String key) {
        l.remove(key);
        return true;
    }

    /**
     * 删除过期时间
     *
     * @param key 缓存key
     * @return true:删除成功
     */
    public boolean removeTimeOut(String key) {
        t.remove(key);
        return true;
    }


}
