package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OtherCenterVO extends UserCenterVO {

    /**
     * 是否可关注
     */
    private Boolean isCanFollow;
}
