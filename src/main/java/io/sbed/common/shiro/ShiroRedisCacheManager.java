package io.sbed.common.shiro;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Description: redis缓存管理器 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:09
 */
@Component
public class ShiroRedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<K,V>(120, redisTemplate);
    }

    /**
     * 为shiro量身定做的一个redis cache,为Authorization cache做了特别优化
     */
    public class ShiroRedisCache<K, V> implements Cache<K, V> {

        private long expireTime = 120;// 缓存的超时时间，单位为s

        private RedisTemplate<K, V> redisTemplate;// 通过构造方法注入该对象

        public ShiroRedisCache() {
            super();
        }

        public ShiroRedisCache(long expireTime, RedisTemplate<K, V> redisTemplate) {
            super();
            this.expireTime = expireTime;
            this.redisTemplate = redisTemplate;
        }

        /**
         * 通过key来获取对应的缓存对象
         * 通过源码我们可以发现，shiro需要的key的类型为Object，V的类型为AuthorizationInfo对象
         */
        @Override
        public V get(K key) throws CacheException {
            return redisTemplate.opsForValue().get(key);
        }

        /**
         * 将权限信息加入缓存中
         */
        @Override
        public V put(K key, V value) throws CacheException {
            redisTemplate.opsForValue().set(key, value, this.expireTime, TimeUnit.SECONDS);
            return value;
        }

        /**
         * 将权限信息从缓存中删除
         */
        @Override
        public V remove(K key) throws CacheException {
            V v = redisTemplate.opsForValue().get(key);
            redisTemplate.opsForValue().getOperations().delete(key);
            return v;
        }

        @Override
        public void clear() throws CacheException {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Set<K> keys() {
            return null;
        }

        @Override
        public Collection<V> values() {
            return null;
        }

    }

}