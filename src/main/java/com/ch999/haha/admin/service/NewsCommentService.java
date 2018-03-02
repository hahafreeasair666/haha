package com.ch999.haha.admin.service;

import com.ch999.haha.admin.vo.CommentReplyVO;
import com.ch999.haha.admin.vo.NewsCommentVO;
import com.ch999.haha.common.PageableVo;
import org.springframework.data.domain.Pageable;


/**
 * @author hahalala
 */
public interface NewsCommentService {

    /**
     * 评论  评论新闻，回去评论，回复回复
     * @param newsId 新闻id 为空就是回复评论
     * @param commentId 评论id 为空就是评论新闻，以上二者不能同时为空
     * @param content 评论内容
     * @return 结果
     */
    Boolean addComment(Integer newsId,String commentId,String content,Integer userId);

    /**
     * 点赞
     * @param id 评论或者回复id
     * @return 结果
     */
    Boolean addZan(String id,Integer userId);

    /**
     * 评论列表
     * @param newsId 新闻id
     * @param pageable 分页对象
     * @param userId 用户id
     * @return 返回
     */
    NewsCommentVO getNewsCommentList(Integer newsId,Pageable pageable,Integer userId);

    /**
     *
     * @param commentId 评论id
     * @param pageable 分页对象
     * @param userId 用户id
     * @return
     */
    CommentReplyVO getCommentReplies(String commentId, PageableVo pageable, Integer userId);
}
