package com.ch999.haha.admin.component;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.ch999.haha.admin.document.redis.UserBO;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.repository.redis.UserBORepository;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.UserInfoService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author hahalala
 */
@Component
public class UserComponent {

    private static final String USERID_PIX = "GetUserInfo:";

    private static final String AUTHORIZATION = "Authorization";

    @Resource
    private UserBORepository userBORepository;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserInfoBORepository userInfoBORepository;


    /**
     * 获取当前用户信息
     *
     * @return
     */
    public UserInfo getLoginUser() {
        Integer userId = getUserId();
        if (userId == -1) {
            return new UserInfo();
        } else {
            UserInfoBO one = userInfoBORepository.findOne(userId);
            if(one == null) {
                UserInfo userInfo = userInfoService.selectById(userId);
                UserInfoBO userInfoBO = new UserInfoBO(userId,userInfo);
                userInfoBORepository.save(userInfoBO);
                return userInfo != null ? userInfo : new UserInfo();
            }else {
                return one.getUserInfo();
            }
        }
    }

    public String getAuthorization(Integer userId, Boolean isLogonFree) {
        final String authorization  = IdWorker.get32UUID();
        UserBO userBO = new UserBO(USERID_PIX + authorization, userId);
        if (isLogonFree) {
            userBO.setLiveTime(604800);
        }
        userBORepository.save(userBO);
        return authorization;
    }

    private Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(AUTHORIZATION);
        UserBO user = userBORepository.findOne(USERID_PIX + token);
        return user == null ? -1 : user.getUserId();
    }
}
