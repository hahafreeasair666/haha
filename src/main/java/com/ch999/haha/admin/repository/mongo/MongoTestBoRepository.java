package com.ch999.haha.admin.repository.mongo;

import com.ch999.haha.admin.document.mongo.MongoTestBO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author hahalala
 */
public interface MongoTestBoRepository extends PagingAndSortingRepository<MongoTestBO, String> {
}
