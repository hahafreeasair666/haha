package com.ch999.haha.admin.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddNewsVO {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String body;

    /**
     * 文章配图，英文逗号拼接的数字(imgs表的主键)
     */
    @JsonIgnore
    private String pic;

    /**
     * 动物种类 狗1 猫2
     */
    @JsonIgnore
    private Integer animalType;

    /**
     * 猫狗的类型
     */
    @JsonIgnore
    private Integer dogOrCatType;

    /**
     * 是否是领养信息
     */
    private Boolean isAdoptionNews;
}
