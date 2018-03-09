package com.ch999.haha.admin.vo;

import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PageVO<T> {

    /**
     * 总页码
     */
    private Integer totalPage;

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 评论内容
     */
    private List<T> list;

    public PageVO(Page<NewsCommentBO> page, Integer currentPage, List<T> commentList) {
        this.totalPage = page.getTotalPages();
        this.currentPage = currentPage;
        this.list = commentList;
    }
}
