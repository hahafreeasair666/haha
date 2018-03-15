package com.ch999.haha.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.News;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.AddNewsVO;
import com.ch999.haha.admin.vo.NewsDetailVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.NewsQueryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface NewsService extends IService<News> {

    Boolean addNews(AddNewsVO addNewsVO, Integer userId, String ip);

    NewsDetailVO getNewsById(Integer id, Integer userId);

    Boolean addNewsZan(Integer id,Integer userId);

    Page<NewsListVO> selectNewsList(Page<NewsListVO> page, NewsQueryVO query);

    /**
     * 收藏，取消收藏公告
     * @param id
     * @param userId
     * @param isCollection
     * @return
     */
    Boolean collectionNews(Integer id,Integer userId,Boolean isCollection);

    Boolean deleteNewsById(Integer id,Integer userId);

    Boolean checkIsCanAdoption(Integer id);
	
}
