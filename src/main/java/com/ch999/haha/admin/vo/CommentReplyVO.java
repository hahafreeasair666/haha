package com.ch999.haha.admin.vo;

import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import com.ch999.haha.common.PageableVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentReplyVO {

    /**
     * 总页码
     */
    private Integer totalPage;

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 评论及其回复内容
     */
    private NewsCommentBO newsCommentAndReply;

    public CommentReplyVO(NewsCommentBO newsCommentBO, PageableVo pageable) {
        if(newsCommentBO == null || newsCommentBO.getIsDel()){
            this.newsCommentAndReply = null;
            this.totalPage = 0;
        }else {
            this.newsCommentAndReply = newsCommentBO;
            this.totalPage = (int)Math.ceil((double) newsCommentBO.getReplies().size()/pageable.getSize());
        }
        this.currentPage = totalPage != 0 ? pageable.getPage() : 0;
    }
}
