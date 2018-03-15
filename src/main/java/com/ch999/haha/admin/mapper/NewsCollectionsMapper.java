package com.ch999.haha.admin.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.NewsCollections;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch999.haha.admin.vo.NewsListVO;
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
public interface NewsCollectionsMapper extends BaseMapper<NewsCollections> {

    List<NewsListVO> selectCollectionList(Page<NewsListVO> page,@Param("userId") Integer userId,@Param("isCollection") Boolean isCollection);

}