package com.ch999.haha.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.document.redis.CommentZanBO;
import com.ch999.haha.admin.entity.*;
import com.ch999.haha.admin.mapper.NewsMapper;
import com.ch999.haha.admin.repository.redis.CommentZanRepository;
import com.ch999.haha.admin.service.*;
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

    @Resource
    private AdoptionService adoptionService;

    @Resource
    private AdoptionFeedBackService adoptionFeedBackService;

    @Override
    public Integer addNews(AddNewsVO addNewsVO, Integer userId, String ip) {
        News news = new News();
        BeanUtils.copyProperties(addNewsVO, news);
        news.setCreateTime(new Date());
        news.setEditTime(new Date());
        news.setCreateUserId(userId);
        news.setIsDel(false);
        news.setZan(0);
        news.setPicture(addNewsVO.getPic());
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
        //插入公告表
        this.insert(news);
        //如果是领养信息插入领养表,不传默认就是领养信息吧
        if (addNewsVO.getIsAdoptionNews() == null || addNewsVO.getIsAdoptionNews()) {
            Adoption adoption = new Adoption(news.getId(), news.getTitle());
            adoptionService.insert(adoption);
        }
        //如果是收养反馈表，插入feedback表
        if(addNewsVO.getAdoptionId() != null){
            AdoptionFeedBack adoptionFeedBack = new AdoptionFeedBack(addNewsVO.getAdoptionId(),news.getId());
            adoptionFeedBackService.insert(adoptionFeedBack);
        }
        return news.getId();
    }

    @Override
    public NewsDetailVO getNewsById(Integer id, Integer userId) {
        NewsDetailVO newsDetailVO = newsMapper.selectNewsDetail(id);
        if (newsDetailVO != null) {
            //是否已赞
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
            //是否能收藏
            Wrapper<NewsCollections> wrapper = new EntityWrapper<>();
            wrapper.eq("userid", userId).eq("newid", id);
            if (newsCollectionsService.selectCount(wrapper) > 0) {
                newsDetailVO.setIsCanCollection(false);
            } else {
                newsDetailVO.setIsCanCollection(true);
            }
            if (StringUtils.isNotBlank(newsDetailVO.getPic())) {
                String[] split = newsDetailVO.getPic().split(",");
                List<String> imgMap = new ArrayList<>();
                List<Imgs> imgs = imgsService.selectBatchIds(Arrays.asList(split));
                imgs.forEach(li -> imgMap.add(li.getImgUrl()));
                newsDetailVO.setPicMap(imgMap);
            }
            //是否能收养
            Wrapper<Adoption> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("adoptionid", newsDetailVO.getId()).eq("isadoption", 0);
            if (CollectionUtils.isNotEmpty(adoptionService.selectList(wrapper1))) {
                newsDetailVO.setIsCanAdoption(true);
            } else {
                newsDetailVO.setIsCanAdoption(false);
            }
            //组装父公告id
            if(newsDetailVO.getParentId() != null){
                News news = this.selectById(newsDetailVO.getParentId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",news.getId());
                map.put("title",news.getTitle());
                newsDetailVO.setParentNews(map);
            }
            //组装反馈公告id
            List<News> news = this.selectList(new EntityWrapper<News>().eq("parentid", id).orderBy("createtime",false));
            if(CollectionUtils.isNotEmpty(news)){
                Map<String,Object> map = new HashMap<>();
                map.put("id",news.get(0).getId());
                map.put("title",news.get(0).getTitle());
                newsDetailVO.setFeedBackNews(map);
            }
        }
        return newsDetailVO;
    }

    @Override
    public Boolean  addNewsZan(Integer id, Integer userId) {
        News news = this.selectById(id);
        if (news == null) {
            return null;
        }
        CommentZanBO one = commentZanRepository.findOne(id.toString());
        if (one != null) {
            if (one.getZanUserList().stream().anyMatch(li -> li.equals(userId))) {
                return false;
            }
            one.getZanUserList().add(userId);
        } else {
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
        newsListVOS.forEach(li -> {
            if (StringUtils.isNotBlank(li.getImgPath())) {
                li.setImgPath(imgsService.selectById(li.getImgPath().split(",")[0]).getImgUrl());
            }else {
                li.setImgPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521114107222&di=44a226a92e386a7e3a11f5a158b4fcfa&imgtype=0&src=http%3A%2F%2Fguangfu.bjx.com.cn%2Fb2b%2FContent%2Fimages%2Fzwtp.gif%3Fbjx_newlogo_v%3D20161230");
            }
        });
        Page<NewsListVO> pageList = new Page<>();
        page.setRecords(newsListVOS);
        return page;
    }

    @Override
    public Boolean collectionNews(Integer id, Integer userId, Boolean isCollection) {
        Wrapper<NewsCollections> wrapper = new EntityWrapper<>();
        wrapper.eq("userid", userId).eq("newid", id).eq("isdel", 0);
        List<NewsCollections> newsCollection = newsCollectionsService.selectList(wrapper);
        if (isCollection) {
            //先检验公告是否存在
            if (this.selectById(id) == null) {
                return null;
            }
            if (CollectionUtils.isNotEmpty(newsCollection)) {
                return false;
            }
            NewsCollections newsCollections = new NewsCollections(userId, id);
            return newsCollectionsService.insert(newsCollections);
        } else {
            if (CollectionUtils.isEmpty(newsCollection)) {
                return false;
            } else {
                return newsCollectionsService.deleteById(newsCollection.get(0).getId());
            }
        }
    }

    @Override
    public Boolean deleteNewsById(Integer id, Integer userId) {
        News news = this.selectById(id);
        if (news == null) {
            return null;
        }
        if (!news.getCreateUserId().equals(userId)) {
            return false;
        }
        boolean a = this.deleteById(id);
        //删除公告要删除相应的关注记录
        if (a) {
            Wrapper<NewsCollections> wrapper = new EntityWrapper<>();
            wrapper.eq("newid", id);
            newsCollectionsService.delete(wrapper);
        }
        return a;
    }

    @Override
    public Boolean checkIsCanAdoption(Integer id) {
        News news = this.selectById(id);
        Wrapper<Adoption> wrapper = new EntityWrapper<>();
        wrapper.eq("adoptionid", id);
        List<Adoption> adoptions = adoptionService.selectList(wrapper);
        if (CollectionUtils.isEmpty(adoptions) || news == null ) {
            return false;
        }
        return news.getIsAdoptionNews() && !adoptions.get(0).getAdoption();
    }
}
