package com.ch999.haha.admin.document.mongo;


import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.ch999.haha.admin.document.redis.CommentZanBO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "NewsComment")
public class NewsCommentBO {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
     * 评论人名字，不存入mongo
     */
    private String userName;

    /**
     * 评论人头像，因会变化不存入mongo
     */
    private String avatar;

    /**
     * 点赞数
     */
    private Integer zan;

    /**
     * 当前登录用户是否点赞该评论，不存入mongo
     */
    private Boolean isPraised;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    private Boolean isDel;

    /**
     * 是否有权限删除该评论，不存入mongo
     */
    private Boolean isCanDel;

    /**
     * 回复列表
     */
    private List<Reply> replies;


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
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
         * 评论人名字，不存入mongo
         */
        private String replyUserName;

        /**
         * 评论人头像，因会变化不存入mongo
         */
        private String replyUserAvatar;

        /**
         * 被回复人id
         */
        private Integer toUserId;

        /**
         * 被回复人的名字，不存入mongo
         */
        private String toUserName;

        /**
         * 回复内容
         */
        private String content;

        /**
         * 点赞数
         */
        private Integer zan;

        /**
         * 当前登录用户是否点赞该评论，不存入mongo
         */
        private Boolean isPraised;

        /**
         * 创建时间
         */
        private String createTime;

        /**
         * 是否删除
         */
        @JsonIgnore
        private Boolean isDel;

        /**
         * 是否有权限删除该评论，不存入mongo
         */
        private Boolean isCanDel;

        Reply(Integer replyUserId, String content, Integer toUserId) {
            this.replyId = IdWorker.get32UUID();
            this.isDel = false;
            this.createTime = SIMPLE_DATE_FORMAT.format(new Date());
            this.zan = 0;
            this.content = content;
            this.replyUserId = replyUserId;
            this.toUserId = toUserId;
        }

    }

    /**
     * 仅作为评论新闻时的构造方法
     *
     * @param newsId  新闻id
     * @param content 内容
     * @param userId  用户id
     */
    public NewsCommentBO(Integer newsId, String content, Integer userId) {
        this.newsId = newsId;
        this.id = IdWorker.get32UUID();
        this.createTime = SIMPLE_DATE_FORMAT.format(new Date());
        this.userId = userId;
        this.zan = 0;
        this.replies = new ArrayList<>();
        this.content = content;
        this.isDel = false;
    }

    /**
     * 增添评论
     * @param newsCommentBO
     * @param content
     * @param userId
     * @param id
     */
    public static void addReply(NewsCommentBO newsCommentBO, String content, Integer userId, String id) {
        Integer toUserId;
        if (newsCommentBO.getId().equals(id)) {
            toUserId = newsCommentBO.getUserId();
        } else {
            toUserId = newsCommentBO.getReplies().parallelStream().filter(li -> li.getReplyId().equals(id))
                    .collect(Collectors.toList()).get(0).getReplyUserId();
        }
        Reply reply = new Reply(userId,content,toUserId);
        newsCommentBO.getReplies().add(reply);
    }

    /**
     *  改变赞的数量
     * @param newsCommentBO
     * @param id
     * @param zanCount
     */
    public static void updateZanCount(NewsCommentBO newsCommentBO,String id,Integer zanCount){
       if(newsCommentBO.getId().equals(id)){
           newsCommentBO.setZan(zanCount);
       }else {
           newsCommentBO.getReplies().forEach(li->{
               if(li.getReplyId().equals(id)){
                   li.setZan(zanCount);
               }
           });
       }
    }

    /**
     * 设置当前用户是否已对评论回复点过赞
     */
    public static void setIsPraised(NewsCommentBO newsCommentBO, CommentZanBO commentZanBO){

    }
}
