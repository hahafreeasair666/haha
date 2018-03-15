package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.NewsCollections;
import com.ch999.haha.admin.mapper.NewsCollectionsMapper;
import com.ch999.haha.admin.service.ImgsService;
import com.ch999.haha.admin.service.NewsCollectionsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.service.NewsService;
import com.ch999.haha.admin.service.UserInfoService;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
@Service
public class NewsCollectionsServiceImpl extends ServiceImpl<NewsCollectionsMapper, NewsCollections> implements NewsCollectionsService {

    @Resource
    private ImgsService imgsService;

    @Resource
    private NewsCollectionsMapper newsCollectionsMapper;


    @Override
    public PageVO<NewsListVO> getMyCollectionOrReleaseList(Page<NewsListVO> page, Integer userId,Boolean isCollection) {
        List<NewsListVO> listVOS = newsCollectionsMapper.selectCollectionList(page, userId,isCollection);
        listVOS.forEach(li->{
            if(StringUtils.isNotBlank(li.getImgPath())){
                li.setImgPath(imgsService.selectById(li.getImgPath().split(",")[0]).getImgUrl());
            }else {
                li.setImgPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521114107222&di=44a226a92e386a7e3a11f5a158b4fcfa&imgtype=0&src=http%3A%2F%2Fguangfu.bjx.com.cn%2Fb2b%2FContent%2Fimages%2Fzwtp.gif%3Fbjx_newlogo_v%3D20161230");
            }
        });
        PageVO<NewsListVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(page.getCurrent());
        pageVO.setTotalPage((int) Math.ceil(page.getTotal() / (double) page.getSize()));
        pageVO.setList(listVOS);
        return pageVO;
    }

}
