package com.ch999.haha.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ch999.haha.admin.entity.News;
import com.ch999.haha.admin.mapper.NewsMapper;
import com.ch999.haha.admin.service.NewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.vo.AddNewsVo;
import com.ch999.haha.common.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;


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

    private static final String IP_URL = URL + "?ak=" + AK +"&ip=%s";

    @Override
    public Boolean addNews(AddNewsVo addNewsVo, Integer userId, String ip) {
        News news = new News();
        BeanUtils.copyProperties(addNewsVo,news);
        news.setCreateTime(new Date());
        news.setEditTime(new Date());
        news.setCreateUserId(userId);
        news.setIsDel(false);
        news.setZan(0);
        String format = String.format(IP_URL, ip);
        String s = HttpClientUtil.get(format);
        Map map = JSONObject.parseObject(s, Map.class);
        if(map.get("status").equals(0)){
            Map content = (Map)map.get("content");
            Object address = content.get("address");
            news.setCreatePlace(address.toString());
        }else {
            news.setCreatePlace("未知");
        }
        return this.insert(news);
    }

    @Override
    public News getNewsById(Integer id) {
        return null;
    }
}
