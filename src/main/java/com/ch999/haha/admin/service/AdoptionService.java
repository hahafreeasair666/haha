package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.Adoption;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.NeedSendAdoptionFeedBackVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-14
 */
public interface AdoptionService extends IService<Adoption> {

    /**
     * 获取用户领养成功后各领养信息还需要发送几次公告
     * @param userId
     * @return
     */
    List<NeedSendAdoptionFeedBackVO> getNeedSendAdoptionFeedBack(Integer userId);
}
