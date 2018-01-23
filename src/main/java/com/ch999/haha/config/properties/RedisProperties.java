package com.ch999.haha.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hahalala
 */
@ConfigurationProperties(prefix = "redis1")
@Getter
@Setter
public class RedisProperties {

    private String host;

    private Integer port;

    private String password;
}
