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
     * 个人描述，个性签名等信息
     */
    private String description;


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

}
