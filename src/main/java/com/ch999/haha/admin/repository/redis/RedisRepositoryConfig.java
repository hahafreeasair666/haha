package com.ch999.haha.admin.repository.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author hahalala
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.ch999.haha.admin.repository.redis",redisTemplateRef = "redisTemplate")
public class RedisRepositoryConfig {
}
