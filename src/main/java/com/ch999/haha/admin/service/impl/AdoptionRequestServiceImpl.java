package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ch999.haha.admin.entity.AdoptionRequest;
import com.ch999.haha.admin.mapper.AdoptionRequestMapper;
import com.ch999.haha.admin.service.AdoptionRequestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-15
 */
@Service
public class AdoptionRequestServiceImpl extends ServiceImpl<AdoptionRequestMapper, AdoptionRequest> implements AdoptionRequestService {



    @Override
    public Boolean addAdoptionRequest(Integer id, Integer userId) {
        //先校验是否申请过
        Wrapper<AdoptionRequest> wrapper = new EntityWrapper<>();
        wrapper.eq("newsid",id).eq("userid",userId);
        if(CollectionUtils.isNotEmpty(this.selectList(wrapper))){
            return false;
        }
        //由于前一步已经验证了能否领养，这一步就直接存到领养请求表里面
        AdoptionRequest AdoptionRequest = new AdoptionRequest(id,userId);
        return this.insert(AdoptionRequest);
    }
}
