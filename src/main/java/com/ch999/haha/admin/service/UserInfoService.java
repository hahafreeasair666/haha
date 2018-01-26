package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.UserInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface UserInfoService extends IService<UserInfo> {

    Integer loginByMobileOrUserName(String type,String loginInfo,String pwd);
}
