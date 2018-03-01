package com.ch999.haha;

import com.ch999.haha.admin.document.mongo.MongoTestBO;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTests {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserInfoBORepository userInfoBORepository;

    @Test
    public void test() {
        UserCenterInfoCountVO userInfoCount = userInfoService.getUserInfoCount(1);
        System.out.println(userInfoCount);
    }

    @Test
    public void test1(){
        UserInfoBO one = userInfoBORepository.findOne(1);
        one.setCreditNum(88);
        userInfoBORepository.save(one);
    }
}
