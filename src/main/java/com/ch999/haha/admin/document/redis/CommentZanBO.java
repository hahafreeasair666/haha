package com.ch999.haha.admin.document.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
@RedisHash("CommentZan")
public class CommentZanBO {

    /**
     * 评论id
     */
    @Id
    private String commentId;

    /**
     * 点过赞的人的集合
     */
    private List<Integer> zanUserList;
}
