package com.ch999.haha.admin.api;

import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;


/**
 * @author hahalala
 */
@RestController
@RequestMapping("/login/api")
@Slf4j
public class LoginApi {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserComponent userComponent;

    /**
     * 可用手机号或用户名登录
     *
     * @param loginInfo
     * @param pwd
     * @return
     */
    @PostMapping("/userLogin/v1")
    public Result userLogin(String loginInfo, String pwd, Boolean isLogonFree) {
        Integer userId1 = userInfoService.loginByMobileOrUserName("username", loginInfo, pwd);
        Integer userId2 = userInfoService.loginByMobileOrUserName("mobile", loginInfo, pwd);
        if (userId1 == null && userId2 == null) {
            log.error("登录失败：" +loginInfo +" " + pwd );
            return Result.error("loginError", "登录失败，用户名或密码错误");
        } else {
            String authorization = userComponent.getAuthorization(userId1 == null ? userId2 : userId1, isLogonFree == null ? false : isLogonFree);
            UserInfo userInfo = userInfoService.selectById(userId1 == null ? userId2 : userId1);
            Map<String, Object> map = new HashMap<>(1);
            map.put("authorization", authorization);
            map.put("userId",userInfo.getId());
            map.put("userName",userInfo.getUsername());
            map.put("avatar",userInfo.getPicPath());
            log.info("登录成功");
            return Result.success("success", "登录成功", map);
        }
    }

    /**
     * 登出接口
     * @return
     */
    @GetMapping("/userLoginOut/v1")
    public Result loginOut() {
        Boolean aBoolean = userComponent.loginOut();
        if(aBoolean){
            return Result.success("success","退出登录成功", true);
        }
        return Result.error("error","退出登录失败,用户未登录");
    }

    /**
     * 注册接口
     * @param registerVO
     * @return
     */
    @PostMapping("/userRegister/v1")
    public Result userRegister(@Valid RegisterVO registerVO){
        Integer integer = userComponent.userRegister(registerVO);
        if(integer != null){
            Map<String,Object> map = new HashMap<>(1);
            map.put("userId",integer);
            return Result.success("success","注册成功",map);
        }
        return Result.error("error","注册失败，或两次密码输入不一致");
    }

    /**
     * 检测用户名和手机号是否已被使用
     * @param type
     * @param info
     * @return
     */
    @GetMapping("/checkCanUse/v1")
    public Result checkCanUse(String type,String info){
        if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(info)) {
            if(userComponent.checkCanUse(type, info)){
                return Result.success("success","恭喜该信息可以使用",true);
            }
            return Result.error("haveUsed","很遗憾，该信息已被他人使用");
        }
        return Result.paramError("paramError","请传入要校验的参数");
    }

}
