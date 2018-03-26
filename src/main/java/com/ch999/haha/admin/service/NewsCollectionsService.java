package com.ch999.haha.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.NewsCollections;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.CollectionNewsListVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface NewsCollectionsService extends IService<NewsCollections> {

    PageVO<CollectionNewsListVO> getMyCollectionOrReleaseList(Page<NewsListVO> page, Integer userId, Boolean isCollection);

}
