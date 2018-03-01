package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.mapper.UserInfoMapper;
import com.ch999.haha.admin.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.vo.UserCenterVO;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private UserInfoMapper userInfoMapper;

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

    @Override
    public UserCenterInfoCountVO getUserInfoCount(Integer userId) {
        return userInfoMapper.selectUserInfoCount(userId);
    }

    @Override
    public UserCenterVO getUserCenterInfo(UserInfoBO userInfo) {
        UserCenterVO userCenterVO = new UserCenterVO();
        UserCenterInfoCountVO userInfoCount = getUserInfoCount(userInfo.getUserInfo().getId());
        userCenterVO.setUserName(userInfo.getUserInfo().getUsername());
        userCenterVO.setFans(userInfoCount.getFans());
        userCenterVO.setFollows(userInfoCount.getFollow());
        userCenterVO.setMyCollection(userInfoCount.getCollections());
        userCenterVO.setUserId(userInfoCount.getUserId());
        userCenterVO.setZan(userInfoCount.getZan());
        userCenterVO.setDescription(userInfo.getUserInfo().getAutograph());
        userCenterVO.setMyCredit(userInfo.getCreditInfo().get("creditNum")!=null?(int)userInfo.getCreditInfo().get("creditNum"):0);
        userCenterVO.setAvatar(userInfo.getUserInfo().getPicPath());
        userCenterVO.setMyRelease(userInfoCount.getReleases());
        return userCenterVO;
    }
}
