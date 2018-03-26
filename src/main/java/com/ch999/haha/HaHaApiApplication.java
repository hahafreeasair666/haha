package com.ch999.haha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hahalala
 */
@MapperScan("com.ch999.**.mapper*")
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableEurekaClient
@EnableDiscoveryClient
@EnableRedisRepositories(basePackages = "com.ch999.haha.admin.repository.redis")
@EnableMongoRepositories(basePackages = "com.ch999.haha.admin.repository.mongo")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class HaHaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaHaApiApplication.class, args);
	}
}
