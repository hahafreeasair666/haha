package com.ch999.haha.admin.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * 发布者id
     */
    private Integer userId;

    /**
     * 发布者名字
     */
    private String userName;

    /**
     * 发布者头像
     */
    private String avatar;

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
     * 是否能收藏
     */
    private Boolean isCanCollection;

    /**
     * 是否能收养，不能收养的包括不是收养信息和已被收养的
     */
    private Boolean isCanAdoption;

    /**
     * 是否是收养信息
     */
    private Boolean isAdoptionNews;

    /**
     * 父公告id
     */
    @JsonIgnore
    private Integer parentId;

    /**
     * 所关联的父公告id
     */
    private Map<String,Object> parentNews;

    /**
     * 反馈公告的id
     */
    private Map<String,Object> feedBackNews;
    /**
     * 文章配图
     */
    private List<String> picMap;
}
