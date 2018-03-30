/*
 * Copyright (c) 2006-2017, Yunnan Sanjiu Network technology Co., Ltd.
 *
 * All rights reserved.
 */
package com.ch999.haha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig implements CachingConfigurer {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean(name = "jsonStringRedisTemplate")
    public StringRedisTemplate jsonStringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        stringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        return stringRedisTemplate;
    }

    @Bean(name = "oneHourCacheManager")
    public RedisCacheManager oneHourCacheManager(@Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        redisCacheManager.setDefaultExpiration(3600L);
        return redisCacheManager;
    }

    @Bean(name = "sixHourCacheManager")
    public RedisCacheManager sixHourCacheManager(@Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        redisCacheManager.setDefaultExpiration(21600L);
        return redisCacheManager;
    }
    @Bean(name = "oneDayCacheManager")
    public RedisCacheManager oneDayCacheManager(@Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        redisCacheManager.setDefaultExpiration(86400L);
        return redisCacheManager;
    }

    @Bean(name = "fiveMinuteCacheManager")
    public RedisCacheManager fiveMinuteCacheManager(@Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        redisCacheManager.setDefaultExpiration(300L);
        return redisCacheManager;
    }

    @Bean(name = "oneMinuteCacheManager")
    public RedisCacheManager oneMinuteCacheManager(@Qualifier("redisCacheManager") RedisCacheManager redisCacheManager) {
        redisCacheManager.setDefaultExpiration(60L);
        return redisCacheManager;
    }

    /**
     * 默认缓存处理器
     *
     * @return
     */
    @Bean(name = "cacheManager")
    @Primary
    @Override
    public CacheManager cacheManager() {
        return new RedisCacheManager(jsonStringRedisTemplate());
    }

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager(jsonStringRedisTemplate());
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

}
