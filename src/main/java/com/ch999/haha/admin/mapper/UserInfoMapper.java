package com.ch999.haha.admin.mapper;

import com.ch999.haha.admin.entity.UserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch999.haha.admin.vo.mappervo.UserCenterInfoCountVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 获取某用户粉丝数点赞数等数据
     * @param userId
     * @return
     */
    UserCenterInfoCountVO selectUserInfoCount(@Param("userId") Integer userId);
}