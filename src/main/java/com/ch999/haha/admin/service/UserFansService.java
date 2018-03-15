package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.UserFans;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface UserFansService extends IService<UserFans> {

    PageVO<UserInfo> getUserFansOrFollows(Integer userId, Boolean isFansInfo, Integer current);

    Boolean followOrCancel(Integer userId1,Integer userId2,Boolean isFollow);
	
}
