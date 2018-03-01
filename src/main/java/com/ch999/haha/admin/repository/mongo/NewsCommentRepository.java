package com.ch999.haha.admin.repository.mongo;

import com.ch999.haha.admin.document.mongo.NewsCommentBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author hahalala
 */
public interface NewsCommentRepository extends PagingAndSortingRepository<NewsCommentBO,String> {

    /**
     * 用回复id查询整条评论
     * @param replyId 回复id
     * @return 返回
     */
    @Query("{'replies.ReplyId':'?0'}")
    NewsCommentBO findByHotReplyReplyId(String replyId);

    /**
     * 分页查询评论
     * @param newsId 新闻id
     * @param isDel 是否已删除
     * @param pageable
     * @return
     */
    Page<NewsCommentBO> findAllByNewsIdAndIsDel(Integer newsId, Boolean isDel, Pageable pageable);

}
