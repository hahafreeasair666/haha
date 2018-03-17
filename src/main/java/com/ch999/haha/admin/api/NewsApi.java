package com.ch999.haha.admin.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import com.ch999.haha.admin.document.redis.UserInfoBO;
import com.ch999.haha.admin.service.AdoptionRequestService;
import com.ch999.haha.admin.service.AdoptionService;
import com.ch999.haha.admin.service.NewsCommentService;
import com.ch999.haha.admin.service.NewsService;
import com.ch999.haha.admin.vo.*;
import com.ch999.haha.common.HttpClientUtil;
import com.ch999.haha.common.PageableVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hahalala
 */
@RestController
@RequestMapping("/news/api")
public class NewsApi {

    @Resource
    private UserComponent userComponent;

    @Resource
    private NewsCommentService newsCommentService;

    @Resource
    private NewsService newsService;

    @Resource
    private AdoptionRequestService AdoptionRequestService;

    @Resource
    private AdoptionService adoptionService;


    //todo 以下是新闻相关


    @PostMapping("/addNews/v1")
    public Result addNews(AddNewsVO addNewsVO, HttpServletRequest request) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.error("error", "请登录后再操作");
        }
        if((int)loginUser.getCreditInfo().get("creditNum") < 70){
            return Result.error("error", "对不起信誉积分不足不能发布公告");
        }
        String ip = HttpClientUtil.getIpAddr(request);
        if (newsService.addNews(addNewsVO, loginUser.getId(), ip)) {
            return Result.success();
        }
        return Result.error("error", "添加失败");
    }

    @GetMapping("/getNewsDetailById/v1")
    public Result<NewsDetailVO> getNewsDetailById(Integer id) {
        if (id == null) {
            return Result.error("error", "请传入新闻id");
        }
        NewsDetailVO newsById = newsService.getNewsById(id, userComponent.getLoginUser().getId());
        if (newsById == null) {
            return Result.error("error", "该新闻不存在或已被作者删除");
        }
        return Result.success(newsById);
    }

    @PostMapping("/addNewsZan/v1")
    public Result<String> addNewsZan(Integer id) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if (id == null) {
            return Result.error("error", "请传入要点赞的新闻id");
        }
        Boolean aBoolean = newsService.addNewsZan(id, userId);
        if (aBoolean == null) {
            return Result.error("error", "点赞失败，新闻不存在或已被删除");
        } else if (aBoolean) {
            return Result.success();
        }
        return Result.error("error", "您已点过赞了");
    }

    @GetMapping("/getNewsList/v1")
    public Result<PageVO<NewsListVO>> getNewsList(Page<NewsListVO> page, NewsQueryVO query) {
        Page<NewsListVO> newsListVOPage = newsService.selectNewsList(page, query);
        PageVO<NewsListVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(newsListVOPage.getCurrent());
        pageVO.setTotalPage((int) Math.ceil(newsListVOPage.getTotal() / (double) page.getSize()));
        pageVO.setList(newsListVOPage.getRecords());
        return Result.success(pageVO);
    }

    @PostMapping("/collectionNews/v1")
    public Result<String> collectionNews(Integer id) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if (id == null) {
            return Result.error("error", "请选择要收藏的新闻");
        }
        Boolean aBoolean = newsService.collectionNews(id, userId, true);
        if (aBoolean == null) {
            return Result.error("error", "关注失败，公告不存在或已被删除");
        } else if (aBoolean) {
            return Result.success();
        }
        return Result.error("error", "您已收藏该公告，无需重复收藏");
    }

    @PostMapping("/cancelCollectionNews/v1")
    public Result<String> cancelCollectionNews(Integer id) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if (id == null) {
            return Result.error("error", "请选择要取关的新闻");
        }
        if (newsService.collectionNews(id, userId, false)) {
            return Result.success();
        }
        return Result.error("error", "取关失败，您未关注本公告");
    }

    @PostMapping("/deleteNewsById/v1")
    public Result<String> deleteNewsById(Integer id) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        }
        if (id == null) {
            return Result.error("unLogin", "请选择要删除的公告");
        }
        Boolean aBoolean = newsService.deleteNewsById(id, userId);
        if (aBoolean == null) {
            return Result.error("unLogin", "公告不存在或已被删除");
        } else if (aBoolean) {
            return Result.success();
        }
        return Result.error("error", "该公告不是您发布的，您无权删除");
    }

    @PostMapping("/iWantToAdoption/v1")
    public Result<String> iWantToAdoption(Integer id) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        }
        if((int)loginUser.getCreditInfo().get("creditNum") < 100){
            return Result.error("error", "对不起信誉积分不足不能申请领养");
        }
        if (id == null) {
            return Result.error("unLogin", "请选择要领养的宠物");
        }
        if (newsService.checkIsCanAdoption(id)) {
            return AdoptionRequestService.addAdoptionRequest(id, loginUser.getId()) ? Result.success() : Result.error("error", "您已申请过领养无需重复申请");
        }
        return Result.error("error", "该宠物已被领养");
    }

    //领养后，以领养时间计算每周一次，连续3周，每月不低于4次，持续一月算是任务完成
    @GetMapping("/getNeedSendAdoptionFeedBack/v1")
    public Result<List<NeedSendAdoptionFeedBackVO>> getNeedSendAdoptionFeedBack(){
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        }
        return Result.success(adoptionService.getNeedSendAdoptionFeedBack(userId));
    }
    //todo  以下是评论相关


    @PostMapping("/addComment/v1")
    public Result<String> addComment(Integer newsId, String commentId, String content) {
        UserInfoBO loginUser = userComponent.getLoginUser();
        if (loginUser.getId() == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if((int)loginUser.getCreditInfo().get("creditNum") < 70){
            return Result.error("error", "对不起信誉积分不足不能发起评论");
        } else if (newsId == null && StringUtils.isBlank(commentId)) {
            return Result.error("error", "请传入要评论的文章或回复的评论");
        } else if (StringUtils.isBlank(content)) {
            return Result.error("content is null", "请输入评论内容");
        }
        if (newsCommentService.addComment(newsId, commentId, content, loginUser.getId())) {
            return Result.success();
        }
        return Result.error("error", "评论失败");
    }

    @PostMapping("/addCommentZan/v1")
    public Result<String> addCommentZan(String commentId) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if (StringUtils.isBlank(commentId)) {
            return Result.error("error", "请传入要点赞的评论id");
        }
        Boolean aBoolean = newsCommentService.addZan(commentId, userId);
        if (aBoolean == null) {
            return Result.error("error", "点赞失败，评论不存在或已被删除");
        } else if (aBoolean) {
            return Result.success();
        }
        return Result.error("error", "您已点过赞了");
    }

    @GetMapping("/getNewsComment/v1")
    public Result<PageVO<NewsCommentBO>> getNewsComment(Integer newsId, PageableVo pageableVo) {
        //之前还要校验一下此新闻是否存在
        if (pageableVo.getSort() == null) {
            pageableVo.setSort(new Sort(Sort.Direction.DESC, "createTime"));
        }
        PageVO newsCommentList = newsCommentService.getNewsCommentList(newsId, pageableVo, userComponent.getLoginUser().getId());
        if (CollectionUtils.isEmpty(newsCommentList.getList())) {
            return Result.success("success", "暂无评论", null);
        }
        return Result.success(newsCommentList);
    }

    @GetMapping("/getCommentReply/v1")
    public Result<CommentReplyVO> getNewsReply(String commentId, PageableVo pageableVo) {
        if (StringUtils.isBlank(commentId)) {
            return Result.paramError("error", "请传入评论id");
        }
        CommentReplyVO commentReplies = newsCommentService.getCommentReplies(commentId, pageableVo, userComponent.getLoginUser().getId());
        if (commentReplies.getNewsCommentAndReply() == null) {
            return Result.error("error", "该评论不存在或已删除");
        }
        return Result.success(commentReplies);
    }

    @PostMapping("/deleteCommentOrReplyById/v1")
    public Result<String> deleteCommentOrReplyById(String id) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        }
        if (StringUtils.isBlank(id)) {
            return Result.error("error", "请选择要删除的评论");
        }
        Boolean aBoolean = newsCommentService.deleteCommentOrReplyById(id, userId);
        if (aBoolean == null) {
            return Result.error("unLogin", "评论不存在或已被删除");
        } else if (aBoolean) {
            return Result.success();
        }
        return Result.error("error", "该评论不是您发布的，您无权删除");
    }
}
