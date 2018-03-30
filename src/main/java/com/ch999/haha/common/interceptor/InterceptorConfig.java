/*
 * Copyright (c) 2006-2017, Yunnan Sanjiu Network technology Co., Ltd.
 *
 * All rights reserved.
 */
package com.ch999.haha.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 拦截器配置
 *
 * @author yangwei
 */
@Configuration
@Profile(value = {"test"})
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Resource
    private SimpleInterceptor simpleInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(simpleInterceptor).addPathPatterns("/**");
    }


}
