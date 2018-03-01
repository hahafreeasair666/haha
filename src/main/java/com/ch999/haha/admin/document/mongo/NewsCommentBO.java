package com.ch999.haha.admin.document.mongo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@Document(collection = "NewsComment")
public class NewsCommentBO {

    /**
     * 评论id
     */
    @Id
    private String id;

    /**
     * 资讯id
     */
    @Indexed
    private Integer newsId;

    /**
     * 评论人的id
     */
    private Integer userId;

    /**
     * 点赞数
     */
    private Integer zan;

    /**
     * 评论的内容
     */
    private String comment;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 回复列表
     */
    private List<Reply> replies;


    @Getter
    @Setter
    public static class Reply {

        /**
         * 回复id
         */
        private String replyId;

        /**
         * 回复人id
         */
        private Integer replyUserId;

        /**
         * 被回复人id
         */
        private Integer toUserId;

        /**
         * 回复内容
         */
        private String comment;

        /**
         * 点赞数
         */
        private Integer zan;

        /**
         * 创建时间
         */
        private String createTime;

        /**
         * 是否删除
         */
        private Integer isDel;

    }

}
