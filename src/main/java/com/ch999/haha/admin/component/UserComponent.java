package com.ch999.haha.admin.component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.ch999.haha.admin.document.redis.UserBO;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.Imgs;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.repository.redis.UserBORepository;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.ImgService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.RegisterVO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private ImgService imgService;


    /**
     * 获取当前用户信息
     *
     * @return
     */
    public UserInfoBO getLoginUser() {
        Integer userId = getUserId();
        if (userId == -1) {
            return new UserInfoBO();
        } else {
            UserInfoBO one = userInfoBORepository.findOne(userId);
            if (one == null) {
                UserInfo userInfo = userInfoService.selectById(userId);
                UserInfoBO userInfoBO = new UserInfoBO(userId, userInfo);
                userInfoBORepository.save(userInfoBO);
                return userInfo != null ? userInfoBO : new UserInfoBO();
            } else {
                return one;
            }
        }
    }

    public String getAuthorization(Integer userId, Boolean isLogonFree) {
        final String authorization = IdWorker.get32UUID();
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

    public Boolean loginOut() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        userBORepository.delete(USERID_PIX + token);
        UserBO user = userBORepository.findOne(USERID_PIX + token);
        return user == null;
    }

    /**
     * 用户注册
     * @param registerVO
     * @return
     */
    public Integer userRegister(RegisterVO registerVO) {
        if (!registerVO.getPwd1().equals(registerVO.getPwd2())) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerVO.getUserName());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPwd(registerVO.getPwd1());
        userInfoService.insert(userInfo);
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", userInfo.getMobile());
        //注册的时候就给用户一个初始信用积分
        UserInfo userInfo1 = userInfoService.selectOne(wrapper);
        UserInfoBO userInfoBO = new UserInfoBO(userInfo1.getId(), userInfo1);
        userInfoBORepository.save(userInfoBO);
        return userInfo1.getId();
    }

    /**
     * 对注册用户的用户名和手机号进行是否可用的校验
     * @param type
     * @param checking
     * @return
     */
    public Boolean checkCanUse(String type, String checking) {
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        if("mobile".equals(type)) {
            return userInfoService.selectList(wrapper).parallelStream().filter(li -> li.getMobile().equals(checking)).count()==0;
        }else {
            return userInfoService.selectList(wrapper).parallelStream().filter(li -> li.getUsername().equals(checking)).count()==0;
        }
    }

    public Boolean updateUserAvatar(Integer userId,MultipartFile file){
        Imgs imgs = imgService.uploadImg(file);
        if(imgs == null){
            return false;
        }else {
            UserInfo userInfo = new UserInfo(userId);
            userInfo.setPicPath(imgs.getImgUrl());
            userInfoService.updateById(userInfo);
            UserInfo userInfo1 = userInfoService.selectById(userId);
            UserInfoBO one = userInfoBORepository.findOne(userId);
            one.setUserInfo(userInfo1);
            userInfoBORepository.save(one);
            return true;
        }
    }
}
