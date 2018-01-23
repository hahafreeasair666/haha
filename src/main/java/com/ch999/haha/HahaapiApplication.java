package com.ch999.haha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hahalala
 */
@MapperScan("com.ch999.**.mapper*")
@EnableAutoConfiguration
@EnableCaching
@EnableTransactionManagement
@EnableRedisRepositories(basePackages = "com.ch999.haha.admin.repository.redis")
@SpringBootApplication
public class HahaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HahaapiApplication.class, args);
	}
}
