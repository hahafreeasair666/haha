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

    /**
     * 我的收养
     */
    private Integer myAdoption;

    /**
     * 收养请求
     */
    private Integer adoptionRequest;

    /**
     * 领养成功的人
     */
    private Integer adoptionPerson;
}
