package com.ch999.haha.admin.repository.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.ch999.haha.admin.repository.mongo", mongoTemplateRef = "mongoTemplateTest")
public class CommentMongoRepository {
}
