package com.ch999.haha.config;

import com.ch999.haha.config.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean(name = "redisTemplate")
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory(redisProperties.getHost(), redisProperties.getPort(),
                redisProperties.getPassword()));
        return redisTemplate;
    }

    private RedisConnectionFactory connectionFactory(String hostName, int port, String password) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (StringUtils.isNotEmpty(password)) {
            jedis.setPassword(password);
        }

        // 初始化连接pool
        jedis.afterPropertiesSet();

        return jedis;
    }
}
