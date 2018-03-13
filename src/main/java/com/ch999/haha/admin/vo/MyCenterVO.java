package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyCenterVO extends UserCenterVO {

    /**
     * 获得的赞
     */
    private Integer zan;

    /**
     * 我的发布
     */
    private Integer myRelease;

    /**
     * 我的收藏
     */
    private Integer myCollection;
}
