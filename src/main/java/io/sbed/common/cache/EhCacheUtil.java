package io.sbed.common.cache;

import org.apache.poi.ss.formula.functions.T;
import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description: 缓存工具类 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/19 上午9:38
 */
public class EhCacheUtil {
    /**
     * 缓存管理器
     */
    private static CacheManager cacheManager = null;

    /**
     * 无到期时间的缓存域
     */
    public static Cache<String, String> noExpiryCache = null;

    /**
     * 有到期时间的缓存域，有效期：10分钟
     */
    public static Cache<String, String> expiryCache = null;

    static {
        // 获取ehcache配置文件路径
        URL url = EhCacheUtil.class.getResource("/ehcache.xml");
        // 加载ehcache配置文件
        Configuration xmlConfig = new XmlConfiguration(url);
        // 获取ehcache管理器
        cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        // 初始化ehcache管理器
        cacheManager.init();

        // 获取无到期时间的缓存域
        noExpiryCache = getCache("noExpiryCache", String.class, String.class);
        // 获取有到期时间的缓存域，有效期：10分钟
        expiryCache = getCache("noExpiryCache", String.class, String.class);
    }

    /**
     * 获取EhCahce缓存域
     *
     * @param alias     缓存域名称
     * @param keyType   键类型
     * @param valueType 值类型
     * @return
     */
    public static <K, V> Cache<K, V> getCache(String alias, Class<K> keyType, Class<V> valueType) {
        return cacheManager.getCache(alias, keyType, valueType);
    }

    /**
     * 获取缓存域中的所有键值对
     *
     * @return
     */
    public static <K, V> Map<K, V> iterator(Cache<K, V> cache) {
        Map<K, V> map = null;
        map = new HashMap<K, V>();
        // 循环取出缓存域中的键和值
        for (Entry<K, V> entry : cache) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static Cache getCache(String cacheName) {
        return noExpiryCache;
    }


    public static void put(String cacheName, Object key, Object value) {
        getCache(cacheName).put(key, value);
    }


    public static <T> T get(String cacheName, Object key) {
        return (T) getCache(cacheName).get(key);
    }


    public static List<T> getKeys(String cacheName) {
        List list = new LinkedList();
        list.addAll(iterator(getCache(cacheName)).entrySet());
        return list;
    }


    public static void remove(String cacheName, Object key) {
        getCache(cacheName).remove(key);
    }


    public static void removeAll(String cacheName) {
        getCache(cacheName).clear();
    }

    public static void main(String[] args) {
        iterator(noExpiryCache);
    }
}