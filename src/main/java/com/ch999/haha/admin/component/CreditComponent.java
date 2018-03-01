package com.ch999.haha.admin.component;

import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户信用分管理控制类
 * @author hahalala
 */
@Component
public class CreditComponent {

    @Resource
    private UserInfoBORepository userInfoBORepository;

    /**
     * 对用户信用积分进行增减
     * @param userId
     * @param number
     * @param isIncrease
     */
    public void updateUserCredit(Integer userId,Integer number,Boolean isIncrease){
        UserInfoBO one = userInfoBORepository.findOne(userId);
        Integer creditNum;
        if(isIncrease){
             creditNum = (int)one.getCreditInfo().get("creditNum") + number;
        }else {
             creditNum = (int)one.getCreditInfo().get("creditNum") - number;
        }
        one.getCreditInfo().put("creditNum",creditNum);
        one.getCreditInfo().put("updateTime",new Date());
        userInfoBORepository.save(one);
    }

    /*@Scheduled(fixedRate = 5000*1)
    public void testTimedTask(){
        System.out.println("哈哈自在如风");
    }*/

}
