package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.UserFans;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.mapper.UserInfoMapper;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.UserFansService;
import com.ch999.haha.admin.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.vo.MyCenterVO;
import com.ch999.haha.admin.vo.OtherCenterVO;
import com.ch999.haha.admin.vo.UserCenterVO;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoBORepository userInfoBORepository;

    @Resource
    private UserFansService userFansService;

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
    public MyCenterVO getMyCenterInfo(UserInfoBO userInfo) {
        MyCenterVO userCenterVO = new MyCenterVO();
        UserCenterInfoCountVO userInfoCount = getUserInfoCount(userInfo.getUserInfo().getId());
        userCenterVO.setUserName(userInfo.getUserInfo().getUsername());
        userCenterVO.setFans(userInfoCount.getFans());
        userCenterVO.setFollows(userInfoCount.getFollow());
        userCenterVO.setMyCollection(userInfoCount.getCollections());
        userCenterVO.setMyAdoption(userInfoCount.getMyAdoption());
        userCenterVO.setAdoptionRequest(userInfoCount.getAdoptionRequest());
        userCenterVO.setUserId(userInfoCount.getUserId());
        userCenterVO.setZan(userInfoCount.getZan());
        userCenterVO.setDescription(userInfo.getUserInfo().getAutograph());
        userCenterVO.setMyCredit(userInfo.getCreditInfo().get("creditNum")!=null?(int)userInfo.getCreditInfo().get("creditNum"):0);
        userCenterVO.setAvatar(userInfo.getUserInfo().getPicPath());
        userCenterVO.setMyRelease(userInfoCount.getReleases());
        userCenterVO.setAdoptionPerson(userInfoCount.getAdoptionPerson());
        return userCenterVO;
    }

    @Override
    public OtherCenterVO getUserCenterInfo(Integer userId,Integer loginUserId) {
        OtherCenterVO userCenterVO = new OtherCenterVO();
        UserCenterInfoCountVO userInfoCount = getUserInfoCount(userId);
        userCenterVO.setUserId(userId);
        userCenterVO.setFollows(userInfoCount.getFollow());
        userCenterVO.setFans(userInfoCount.getFans());
        UserInfoBO one = userInfoBORepository.findOne(userId);
        log.error("用户查询失败，redis可能被清楚数据 id:"+ userId);
        if(one == null){
            return null;
        }
        userCenterVO.setAvatar(one.getUserInfo().getPicPath());
        userCenterVO.setDescription(one.getUserInfo().getAutograph());
        userCenterVO.setMyCredit(one.getCreditInfo().get("creditNum")!=null?(int)one.getCreditInfo().get("creditNum"):0);
        userCenterVO.setUserName(one.getUserInfo().getUsername());
        //组装是否已关注信息
        if(loginUserId != null){
            Wrapper<UserFans> wrapper = new EntityWrapper<>();
            wrapper.eq("userid1",loginUserId);
            wrapper.eq("userid2",userId);
            if(userFansService.selectCount(wrapper) == 0 && !userId.equals(loginUserId)){
                userCenterVO.setIsCanFollow(true);
            }else {
                userCenterVO.setIsCanFollow(false);
            }
        }else {
            userCenterVO.setIsCanFollow(true);
        }
        return userCenterVO;
    }
}
