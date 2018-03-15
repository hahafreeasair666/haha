package com.ch999.haha.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.haha.admin.entity.Adoption;
import com.ch999.haha.admin.entity.AdoptionRequest;
import com.ch999.haha.admin.entity.News;
import com.ch999.haha.admin.mapper.AdoptionMapper;
import com.ch999.haha.admin.mapper.AdoptionRequestMapper;
import com.ch999.haha.admin.service.AdoptionRequestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch999.haha.admin.service.AdoptionService;
import com.ch999.haha.admin.service.ImgsService;
import com.ch999.haha.admin.service.NewsService;
import com.ch999.haha.admin.vo.AdoptionRequestVO;
import com.ch999.haha.admin.vo.MyAdoptionVO;
import com.ch999.haha.admin.vo.NewsListVO;
import com.ch999.haha.admin.vo.PageVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-15
 */
@Service
public class AdoptionRequestServiceImpl extends ServiceImpl<AdoptionRequestMapper, AdoptionRequest> implements AdoptionRequestService {

    @Resource
    private AdoptionRequestMapper adoptionRequestMapper;

    @Resource
    private NewsService newsService;

    @Resource
    private ImgsService imgsService;

    @Override
    public Boolean addAdoptionRequest(Integer id, Integer userId) {
        //先校验是否申请过
        Wrapper<AdoptionRequest> wrapper = new EntityWrapper<>();
        wrapper.eq("newsid", id).eq("userid", userId);
        if (CollectionUtils.isNotEmpty(this.selectList(wrapper))) {
            return false;
        }
        //由于前一步已经验证了能否领养，这一步就直接存到领养请求表里面
        AdoptionRequest AdoptionRequest = new AdoptionRequest(id, userId);
        return this.insert(AdoptionRequest);
    }

    @Override
    public PageVO<MyAdoptionVO> getMyAdoptionList(Integer userId, Integer currentPage) {
        Integer size = 10;
        Wrapper<AdoptionRequest> wrapper = new EntityWrapper<>();
        wrapper.eq("userid", userId);
        List<MyAdoptionVO> list = new ArrayList<>();
        List<AdoptionRequest> adoptionRequests = this.selectList(wrapper);
        PageVO<MyAdoptionVO> pageVO = new PageVO<>();
        pageVO.setTotalPage((int) Math.ceil(adoptionRequests.size() / (double) size));
        pageVO.setCurrentPage(currentPage);
        if (CollectionUtils.isNotEmpty(adoptionRequests)) {
            if (adoptionRequests.size() > size * (currentPage - 1)) {
                adoptionRequests = adoptionRequests.subList(size * (currentPage - 1), size * currentPage > adoptionRequests.size() ? adoptionRequests.size() : size * currentPage);
            } else {
                adoptionRequests = new ArrayList<>();
            }
            adoptionRequests.forEach(li -> {
                News news = newsService.selectById(li.getNewsId());
                MyAdoptionVO myAdoptionVO = new MyAdoptionVO();
                myAdoptionVO.setNewsId(li.getNewsId());
                myAdoptionVO.setTitle(news.getTitle());
                myAdoptionVO.setIsSuccess(li.getIsSuccess());
                myAdoptionVO.setPic(getOnePicPath(news));
                list.add(myAdoptionVO);
            });
        }
        pageVO.setList(list);
        return pageVO;
    }

    @Override
    public Page<AdoptionRequestVO> getAdoptionRequestList(Page<AdoptionRequestVO> page, Integer userId) {
        List<AdoptionRequestVO> adoptionRequestVOS = adoptionRequestMapper.selectAdoptionRequestList(page,userId);
        Page<AdoptionRequestVO> pageList = new Page<>();
        pageList.setRecords(adoptionRequestVOS);
        return pageList;
    }

    private String getOnePicPath(News news){
        if(news != null && StringUtils.isNotBlank(news.getPicture())){
          return  imgsService.selectById(news.getPicture().split(",")[0]).getImgUrl();
        }
        return "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521114107222&di=44a226a92e386a7e3a11f5a158b4fcfa&imgtype=0&src=http%3A%2F%2Fguangfu.bjx.com.cn%2Fb2b%2FContent%2Fimages%2Fzwtp.gif%3Fbjx_newlogo_v%3D20161230";
    }
}
