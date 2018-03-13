package com.ch999.haha.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.document.redis.CommentZanBO;
import com.ch999.haha.admin.entity.Imgs;
import com.ch999.haha.admin.entity.News;
import com.ch999.haha.admin.entity.NewsCollections;
import com.ch999.haha.admin.mapper.NewsMapper;
import com.ch999.haha.admin.repository.redis.CommentZanRepository;
import com.ch999.haha.admin.service.ImgsService;
import com.ch999.haha.admin.service.NewsCollectionsService;
import com.ch999.haha.admin.service.NewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.vo.AddNewsVO;
import com.ch999.haha.admin.vo.NewsDetailVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.NewsQueryVO;
import com.ch999.haha.common.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-01-25
 */
@Service
@Slf4j
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    private static final String URL = "https://api.map.baidu.com/location/ip";

    private static final String AK = "2E9486ae97437e84c519f008952b67ae";

    private static final String IP_URL = URL + "?ak=" + AK + "&ip=%s";

    @Resource
    private NewsMapper newsMapper;

    @Resource
    private CommentZanRepository commentZanRepository;

    @Resource
    private ImgsService imgsService;

    @Resource
    private NewsCollectionsService newsCollectionsService;

    @Override
    public Boolean addNews(AddNewsVO addNewsVO, Integer userId, String ip) {
        News news = new News();
        BeanUtils.copyProperties(addNewsVO, news);
        news.setCreateTime(new Date());
        news.setEditTime(new Date());
        news.setCreateUserId(userId);
        news.setIsDel(false);
        news.setZan(0);
        String format = String.format(IP_URL, ip);
        String s = HttpClientUtil.get(format);
        Map map = JSONObject.parseObject(s, Map.class);
        if (map.get("status").equals(0)) {
            Map content = (Map) map.get("content");
            Object address = content.get("address");
            news.setCreatePlace(address.toString());
        } else {
            news.setCreatePlace("未知");
        }
        return this.insert(news);
    }

    @Override
    public NewsDetailVO getNewsById(Integer id, Integer userId) {
        NewsDetailVO newsDetailVO = newsMapper.selectNewsDetail(id);
        if (newsDetailVO != null) {
            if (userId != null && newsDetailVO.getZan() > 0) {
                CommentZanBO one = commentZanRepository.findOne(id.toString());
                if (one != null && one.getZanUserList().stream().anyMatch(userId::equals)) {
                    newsDetailVO.setIsPraised(true);
                } else {
                    newsDetailVO.setIsPraised(false);
                }
            } else {
                newsDetailVO.setIsPraised(false);
            }
            if (StringUtils.isNotBlank(newsDetailVO.getPic())) {
                String[] split = newsDetailVO.getPic().split(",");
                List<String> imgMap = new ArrayList<>();
                List<Imgs> imgs = imgsService.selectBatchIds(Arrays.asList(split));
                imgs.forEach(li -> imgMap.add( li.getImgUrl()));
                newsDetailVO.setPicMap(imgMap);
            }
        }
        return newsDetailVO;
    }

    @Override
    public Boolean addNewsZan(Integer id, Integer userId) {
        News news = this.selectById(id);
        if(news == null){
            return null;
        }
        CommentZanBO one = commentZanRepository.findOne(id.toString());
        if(one != null){
            if(one.getZanUserList().stream().anyMatch(li->li.equals(userId))){
                return false;
            }
            one.getZanUserList().add(userId);
        }else {
            one = new CommentZanBO();
            one.setCommentId(id.toString());
            List<Integer> list = new ArrayList<>();
            list.add(userId);
            one.setZanUserList(list);
        }
        news.setZan(one.getZanUserList().size());
        this.updateById(news);
        commentZanRepository.save(one);
        return true;
    }

    @Override
    public Page<NewsListVO> selectNewsList(Page<NewsListVO> page, NewsQueryVO query) {
        List<NewsListVO> newsListVOS = newsMapper.selectNewsList(page, query);
        newsListVOS.forEach(li->{
            if(StringUtils.isNotBlank(li.getImgPath())){
                li.setImgPath(imgsService.selectById(li.getImgPath().split(",")[0]).getImgUrl());
            }
        });
        Page<NewsListVO> pageList = new Page<>();
        page.setRecords(newsListVOS);
        return page;
    }

    @Override
    public Boolean collectionNews(Integer id, Integer userId,Boolean isCollection) {
        Wrapper<NewsCollections> wrapper = new EntityWrapper<>();
        wrapper.eq("userid",userId).eq("newid",id).eq("isdel",0);
        List<NewsCollections> newsCollection = newsCollectionsService.selectList(wrapper);
        if(isCollection){
            //先检验公告是否存在
            if(this.selectById(id) == null){
                return null;
            }
           if(CollectionUtils.isNotEmpty(newsCollection)){
               return false;
           }
           NewsCollections newsCollections = new NewsCollections(userId,id);
           return newsCollectionsService.insert(newsCollections);
       }else {
           if(CollectionUtils.isEmpty(newsCollection)){
               return false;
           }else {
               return newsCollectionsService.deleteById(newsCollection.get(0).getId());
           }
       }
    }

    @Override
    public Boolean deleteNewsById(Integer id, Integer userId) {
        News news = this.selectById(id);
        if(news == null){
            return null;
        }
        if(!news.getCreateUserId().equals(userId)){
            return false;
        }
        boolean a =  this.deleteById(id);
        //删除公告要删除相应的关注记录
        if(a){
            Wrapper<NewsCollections> wrapper = new EntityWrapper<>();
            wrapper.eq("newid",id);
            newsCollectionsService.delete(wrapper);
        }
        return a;
    }
}
