package com.ch999.haha.admin.service;

import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.entity.UserInfo;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.MyCenterVO;
import com.ch999.haha.admin.vo.OtherCenterVO;
import com.ch999.haha.admin.vo.UserCenterVO;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2018-01-25
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用用户名或手机号登录
     *
     * @param type
     * @param loginInfo
     * @param pwd
     * @return
     */
    Integer loginByMobileOrUserName(String type, String loginInfo, String pwd);

    /**
     * 获取某用户粉丝数点赞数等数据
     *
     * @param userId
     * @return
     */
    UserCenterInfoCountVO getUserInfoCount(Integer userId);

    /**
     * 组装用户中心消息
     *
     * @param userInfo
     * @return
     */
    MyCenterVO getMyCenterInfo(UserInfoBO userInfo);

    OtherCenterVO getUserCenterInfo(Integer userId,Integer loginUserId);
}
