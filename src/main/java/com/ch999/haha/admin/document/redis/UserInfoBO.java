package com.ch999.haha.admin.document.redis;


import com.ch999.haha.admin.entity.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("userInfo")
public class UserInfoBO {

    @Id
    private Integer id;

    private UserInfo userInfo;

    public UserInfoBO(Integer id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
