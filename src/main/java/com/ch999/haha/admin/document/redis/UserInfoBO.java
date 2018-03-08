package com.ch999.haha.admin.document.redis;


import com.ch999.haha.admin.entity.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hahalala
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@RedisHash("userInfo")
public class UserInfoBO {

    @Id
    private Integer id;

    private UserInfo userInfo;

    private Map<String,Object> creditInfo;

    /**
     * 该构造器只有新用户的时候调用
     * @param id
     * @param userInfo
     */
    public UserInfoBO(Integer id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
        //初始信誉分100，信誉分决定用户的权限
        Map<String,Object> map = new HashMap<>();
        map.put("creditNum",100);
        //记录上一次信用分改变的时间
        map.put("updateTime",new Date());
        this.creditInfo = map;
    }

}
