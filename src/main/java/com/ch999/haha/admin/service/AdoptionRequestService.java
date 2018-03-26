package com.ch999.haha.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.AdoptionRequest;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.AdoptionRequestVO;
import com.ch999.haha.admin.vo.MyAdoptionVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.PageVO;

import java.util.List;

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

    PageVO<MyAdoptionVO> getMyAdoptionList(Integer userId,Integer currentPage);

    Page<AdoptionRequestVO> getAdoptionRequestList(Page<AdoptionRequestVO> page, Integer userId,Boolean isRequest);

    Boolean handleAdoptionInfo(Integer loginUserId,Integer adoptionId,Integer userId);

    Boolean cancelAdoptionRequest(Integer userId,Integer adoptionId);

}
