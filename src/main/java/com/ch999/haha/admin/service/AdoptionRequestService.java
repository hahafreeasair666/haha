package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.AdoptionRequest;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-15
 */
public interface AdoptionRequestService extends IService<AdoptionRequest> {

    Boolean addAdoptionRequest(Integer id,Integer userId);

}
