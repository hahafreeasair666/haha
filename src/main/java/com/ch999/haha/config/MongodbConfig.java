package com.ch999.haha.config;

import com.ch999.common.util.utils.Exceptions;
import com.ch999.haha.config.properties.MongodbProperties;
import com.mongodb.MongoClientURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
@EnableConfigurationProperties(MongodbProperties.class)
@Slf4j
public class MongodbConfig {

    @Autowired
    private MongodbProperties mongodbProperties;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Primary
    @Bean("mongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean("mongoTemplateTest")
    public MongoTemplate mongoTemplateChat() {
        MongoClientURI mongoClientURI = new MongoClientURI(mongodbProperties.getUri());
        try {
            SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
            return new MongoTemplate(simpleMongoDbFactory);
        } catch (UnknownHostException e) {
            log.error("链接mongo出错。uri:{}，错误信息:{}", mongodbProperties.getUri(), Exceptions.getStackTraceAsString(e));
        }
        return null;
    }

}
