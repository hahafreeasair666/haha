package com.ch999.haha.admin.document.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("userAuthor")
public class UserBO {

    @Id
    private String id;

    private Integer userId;

    /**
     * 默认用户有效登录时间为一天
     */
    @TimeToLive
    long liveTime = 86400L;

    public UserBO(String id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }
}
