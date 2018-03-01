package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hahalala
 */
@Getter
@Setter
@ToString
public class UserCenterVO {

    private Integer userId;

    private String userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     *我的信誉积分
     */
    private Integer myCredit;

    /**
     * 粉丝数
     */
    private Integer fans;

    /**
     * 关注数
     */
    private Integer follows;

    /**
     * 获得的赞
     */
    private Integer zan;

    /**
     * 个人描述，个性签名等信息
     */
    private String description;

    /**
     * 我的发布
     */
    private Integer myRelease;

    /**
     * 我的收藏
     */
    private Integer myCollection;

    /**
     *默认简介
     * @return
     */
    public String getDescription() {
        if(StringUtils.isBlank(description)){
            description = "他很懒，什么都没有留下~";
        }
        return description;
    }

    /**
     * 默认头像
     * @return
     */
    public String getAvatar() {
        if(StringUtils.isBlank(avatar)){
            avatar = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2376638784,1470858270&fm=27&gp=0.jpg";
        }
        return avatar;
    }
}
