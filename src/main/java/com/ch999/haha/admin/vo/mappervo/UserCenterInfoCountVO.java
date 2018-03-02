package com.ch999.haha.admin.vo.mappervo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
public class UserCenterInfoCountVO {

    @TableField("userid")
    private Integer userId;

    /**
     * 获得的点赞数量
     */
    private Integer zan;

    /**
     * 粉丝数
     */
    private Integer fans;

    /**
     * 关注数
     */
    private Integer follow;

    /**
     * 发布数量
     */
    private Integer releases;

    /**
     * 收藏数量
     */
    private Integer collections;
}