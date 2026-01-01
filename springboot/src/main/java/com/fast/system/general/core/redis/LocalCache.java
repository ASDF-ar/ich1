package com.fast.system.general.core.redis;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类，替代 RedisCache
 *
 * @author fast
 */
@Component
public class LocalCache {

    // 使用 ConcurrentHashMap 作为内存缓存
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    // 存储过期时间的映射
    private final Map<String, Long> expireMap = new ConcurrentHashMap<>();

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        cache.put(key, value);
        expireMap.remove(key); // 永不过期
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        cache.put(key, value);
        long expireTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        expireMap.put(key, expireTime);
    }

    /**
     * 设置有效时间
     *
     * @param key     键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key    键
     * @param timeout 超时时间
     * @param unit   时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        long expireTime = System.currentTimeMillis() + unit.toMillis(timeout);
        expireMap.put(key, expireTime);
        return true;
    }

    /**
     * 获取有效时间
     *
     * @param key 键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        Long expireTime = expireMap.get(key);
        if (expireTime == null) {
            return -1; // 永不过期
        }
        return Math.max(0, expireTime - System.currentTimeMillis());
    }

    /**
     * 判断 key 是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        cleanupExpired();
        return cache.containsKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        cleanupExpired();
        return (T) cache.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 键
     */
    public boolean deleteObject(final String key) {
        cache.remove(key);
        expireMap.remove(key);
        return true;
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 删除的条目数
     */
    public boolean deleteObject(final Collection collection) {
        int count = 0;
        for (Object key : collection) {
            if (deleteObject(key.toString())) {
                count++;
            }
        }
        return count > 0;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        cache.put(key, dataList);
        return dataList.size();
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        cleanupExpired();
        return (List<T>) cache.get(key);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> void setCacheSet(final String key, final Set<T> dataSet) {
        cache.put(key, dataSet);
    }

    /**
     * 获得缓存的set
     *
     * @param key 键
     * @return 值
     */
    public <T> Set<T> getCacheSet(final String key) {
        cleanupExpired();
        return (Set<T>) cache.get(key);
    }

    /**
     * 缓存Map
     *
     * @param key 键
     * @param dataMap 值
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            cache.put(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key 键
     * @return 值
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        cleanupExpired();
        return (Map<String, T>) cache.get(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        Map<String, T> map = getCacheMap(key);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(hKey, value);
        setCacheMap(key, map);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        Map<String, T> map = getCacheMap(key);
        if (map != null) {
            return map.get(hKey);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        List<T> result = new ArrayList<>();
        Map<String, T> map = getCacheMap(key);
        if (map != null) {
            for (Object hKey : hKeys) {
                result.add(map.get(hKey.toString()));
            }
        }
        return result;
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key  键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        Map<String, Object> map = getCacheMap(key);
        if (map != null) {
            Object removed = map.remove(hKey);
            return removed != null;
        }
        return false;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        cleanupExpired();
        List<String> result = new ArrayList<>();
        for (String key : cache.keySet()) {
            if (key.startsWith(pattern.replace("*", ""))) {
                result.add(key);
            }
        }
        return result;
    }

    /**
     * 清理过期的缓存项
     */
    private void cleanupExpired() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> iterator = expireMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            if (currentTime > entry.getValue()) {
                // 过期了，删除
                cache.remove(entry.getKey());
                iterator.remove();
            }
        }
    }
}
