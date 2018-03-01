package com.ch999.haha;

import com.ch999.haha.admin.document.mongo.MongoTestBO;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.repository.redis.UserBORepository;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.NewsCommentService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;
import com.ch999.haha.common.PageableVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTests {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserInfoBORepository userInfoBORepository;

    @Resource
    private UserBORepository userBORepository;

    @Resource
    private NewsCommentService newsCommentService;

    @Test
    public void test() {
        /*UserCenterInfoCountVO userInfoCount = userInfoService.getUserInfoCount(1);
        System.out.println(userInfoCount);*/
       /* userInfoBORepository.deleteAll();
        userBORepository.deleteAll();*/
       PageableVo pageableVo = new PageableVo();
       //pageableVo.setSize(1);
       //pageableVo.setPage(3);
        pageableVo.setSort(new Sort(Sort.Direction.DESC,"createTime"));
       newsCommentService.getNewsCommentList(5,pageableVo,null);
    }

}
