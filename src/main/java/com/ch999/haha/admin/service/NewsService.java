package com.ch999.haha.admin.service;

import com.ch999.haha.admin.entity.News;
import com.baomidou.mybatisplus.service.IService;
import com.ch999.haha.admin.vo.AddNewsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
public interface NewsService extends IService<News> {

    Boolean addNews(AddNewsVo addNewsVo,Integer userId,String ip);

    News getNewsById(Integer id);
	
}
