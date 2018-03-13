package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.UserFans;
import com.ch999.haha.admin.entity.UserInfo;
import com.ch999.haha.admin.mapper.UserFansMapper;
import com.ch999.haha.admin.repository.redis.UserInfoBORepository;
import com.ch999.haha.admin.service.UserFansService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
@Service
public class UserFansServiceImpl extends ServiceImpl<UserFansMapper, UserFans> implements UserFansService {

    @Resource
    private UserInfoService userInfoService;

    @Override
    public List<UserInfo> getUserFansOrFollows(Integer userId, Boolean isFansInfo) {
        Wrapper<UserFans> wrapper = new EntityWrapper<>();
        //粉丝信息
        if(isFansInfo == null || isFansInfo){
            wrapper.eq("userid2",userId);
            List<UserFans> userFans = this.selectList(wrapper);
            List<Integer> idLists = new ArrayList<>();
            userFans.forEach(li->idLists.add(li.getUserId1()));
            if(CollectionUtils.isNotEmpty(idLists)) {
                return userInfoService.selectBatchIds(idLists);
            }
            return new ArrayList<>();
            //关注者信息
        }else {
            wrapper.eq("userid1",userId);
            List<UserFans> userFans = this.selectList(wrapper);
            List<Integer> idLists = new ArrayList<>();
            userFans.forEach(li->idLists.add(li.getUserId2()));
            if(CollectionUtils.isNotEmpty(idLists)) {
                return userInfoService.selectBatchIds(idLists);
            }
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean followOrCancel(Integer userId1, Integer userId2,Boolean isFollow) {
        Wrapper<UserFans> wrapper = new EntityWrapper<>();
        wrapper.eq("userid1",userId1).eq("userid2",userId2);
        List<UserFans> userFans = this.selectList(wrapper);
        if(isFollow == null || isFollow){
            if(CollectionUtils.isNotEmpty(userFans)){
                return false;
            }
            UserFans newUserFans = new UserFans(userId1,userId2);
            return this.insert(newUserFans);
        }else {
            if(CollectionUtils.isEmpty(userFans)){
                return false;
            }
            UserFans userFans1 = userFans.get(0);
            return this.deleteById(userFans1.getId());
        }
    }
}
