package com.ch999.haha.admin.repository.redis;

import com.ch999.haha.admin.document.redis.UserBO;
import org.springframework.data.repository.CrudRepository;

public interface UserBORepository extends CrudRepository<UserBO,String> {
}
