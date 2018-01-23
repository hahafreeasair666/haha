package com.ch999.haha.admin.controller;


import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.Component.UserComponent;
import com.ch999.haha.admin.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-01-22
 */
@RestController
@RequestMapping("/admin/userInfo")
public class UserInfoController {

    @Resource
    private UserComponent userComponent;

    @GetMapping("test")
    public Result test(){
        UserInfo loginUser = userComponent.getLoginUser();
        return Result.success("test","测试",loginUser);
    }
}
