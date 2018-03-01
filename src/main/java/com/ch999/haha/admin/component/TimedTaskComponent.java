package com.ch999.haha.admin.component;

import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务类，主要对用户信用分进行监控从而进行调整
 * @author hahalala
 */
@Component
public class TimedTaskComponent {

    @Resource
    private UserInfoBORepository userInfoBORepository;

    /**
     * 每天凌晨检测用户的信用情况对用户的信用积分进行操作
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateUserCreditNum(){

    }
}
