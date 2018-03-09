package com.ch999.haha.admin.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.News;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch999.haha.admin.vo.NewsDetailVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.NewsQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 查询新闻详情
     * @param id
     * @return
     */
    NewsDetailVO selectNewsDetail(@Param("id") Integer id);

    /**
     * 分页查询新闻列表
     * @param page
     * @param query
     * @return
     */
    List<NewsListVO> selectNewsList(Page<NewsListVO> page, NewsQueryVO query);

}