package com.ch999.haha.admin.api;

import com.ch999.common.util.vo.Result;
import com.ch999.haha.admin.component.UserComponent;
import com.ch999.haha.admin.service.NewsCommentService;
import com.ch999.haha.admin.vo.NewsCommentVO;
import com.ch999.haha.admin.vo.CommentReplyVO;
import com.ch999.haha.common.PageableVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @PostMapping("/addComment/v1")
    public Result<String> addComment(Integer newsId, String commentId, String content) {
        Integer userId = userComponent.getLoginUser().getId();
        if (userId == null) {
            return Result.error("unLogin", "请登录后再进行操作");
        } else if (newsId == null && StringUtils.isBlank(commentId)) {
            return Result.error("error", "请传入要评论的文章或回复的评论");
        } else if (StringUtils.isBlank(content)) {
            return Result.error("content is null", "请输入评论内容");
        }
        if (newsCommentService.addComment(newsId, commentId, content, userId)) {
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
        if (newsCommentService.addZan(commentId, userId)) {
            return Result.success();
        }
        return Result.error("error", "您已点过赞了");
    }

    @GetMapping("/getNewsComment/v1")
    public Result<NewsCommentVO> getNewsComment(Integer newsId, PageableVo pageableVo) {
        //之前还要校验一下此新闻是否存在
        if (pageableVo.getSort() == null) {
            pageableVo.setSort(new Sort(Sort.Direction.DESC, "createTime"));
        }
        NewsCommentVO newsCommentList = newsCommentService.getNewsCommentList(newsId, pageableVo, userComponent.getLoginUser().getId());
        if (CollectionUtils.isEmpty(newsCommentList.getCommentList())) {
            return Result.success("success", "暂无评论", null);
        }
        return Result.success(newsCommentList);
    }

    @GetMapping("/getCommentReply/v1")
    public Result<CommentReplyVO> getNewsReply(String commentId, PageableVo pageableVo) {
        if(StringUtils.isBlank(commentId)){
            return Result.paramError("error","请传入评论id");
        }
        CommentReplyVO commentReplies = newsCommentService.getCommentReplies(commentId, pageableVo, userComponent.getLoginUser().getId());
        if(commentReplies.getNewsCommentAndReply() == null){
            return Result.error("error","该评论不存在或已删除");
        }
        return Result.success(commentReplies);
    }
}
