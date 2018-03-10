package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewsDetailVO extends AddNewsVO {

    /**
     * 新闻id
     */
    private Integer id;

    /**
     * 发布者名字
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 发布地点
     */
    private String createPlace;

    /**
     * 是猫还是狗
     */
    private String type;

    /**
     * 具体猫狗的名称
     */
    private String animalName;

    /**
     * 被赞次数
     */
    private Integer zan;

    /**
     * 当前登录用户是否对其点过赞
     */
    private Boolean isPraised;

    /**
     * 文章配图
     */
    private List<String> picMap;
}
