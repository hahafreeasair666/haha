package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.mapper.UserInfoMapper;
import com.ch999.haha.admin.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public Integer loginByMobileOrUserName(String type, String loginInfo, String pwd) {
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        wrapper.eq(type,loginInfo);
        UserInfo userInfo = this.selectOne(wrapper);
        if(userInfo == null || !pwd.equals(userInfo.getPwd())) {
            return null;
        }else {
            return userInfo.getId();
        }
    }
}
