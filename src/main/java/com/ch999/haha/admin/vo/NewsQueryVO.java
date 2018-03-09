package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewsQueryVO {

    /**
     * 猫狗的种类
     */
    private Integer type;

    /**
     * 具体是什么猫什么狗
     */
    private Integer specificTypes;

    /**
     * 是否是领养信息
     */
    private Boolean isAdoptionNews;

}
