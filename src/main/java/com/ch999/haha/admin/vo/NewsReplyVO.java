package com.ch999.haha.admin.vo;

import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewsReplyVO {

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
}
