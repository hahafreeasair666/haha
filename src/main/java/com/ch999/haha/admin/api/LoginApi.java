package com.ch999.haha.admin.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login/api")
public class LoginApi {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserComponent userComponent;

    /**
     * 可用手机号或用户名登录
     * @param loginInfo
     * @param pwd
     * @return
     */
    @PostMapping("/userLogin")
    public Result userLogin(String loginInfo,String pwd,Boolean isLogonFree){
        Integer userId1 = userInfoService.loginByMobileOrUserName("username", loginInfo, pwd);
        Integer userId2 = userInfoService.loginByMobileOrUserName("mobile", loginInfo, pwd);
        if(userId1 == null && userId2 == null){
            return Result.error("loginError","登录失败，用户名或密码错误");
        }else {
            String authorization = userComponent.getAuthorization(userId1 == null ? userId2 : userId1, isLogonFree==null?false:isLogonFree);
            Map<String,Object> map = new HashMap<>(1);
            map.put("authorization",authorization);
            return Result.success("success","登录成功",map);
        }
    }

}
