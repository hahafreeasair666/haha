package com.ch999.haha.admin.controller;


import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.service.PhoneService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-03-13
 */
@RestController
@RequestMapping("/admin/phone")
public class PhoneController {

    private static final String CK = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[6])|(18[0,5-9])|(19[9]))\\d{8}$";

    @Resource
    private PhoneService phoneService;

    @GetMapping("/checkPhoneNum/v1")
    public Result<String> checkPhoneNum(String mobile, HttpServletResponse httpResponse){
        //设置ajax跨域可访问
        httpResponse.addHeader("Access-Control-Allow-Origin","*");
        if(StringUtils.isBlank(mobile)){
            return Result.error("error","请输入要验证的电话号码");
        }else if(!mobile.matches(CK)){
            return Result.error("error","请输入正确的的电话号码");
        }
        if(phoneService.checkPhoneNum(mobile)){
            return Result.success("success","恭喜该号码可以使用",null);
        }
        return Result.error("error","该号码已被使用");
    }

}

