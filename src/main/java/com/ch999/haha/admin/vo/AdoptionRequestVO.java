package com.ch999.haha.admin.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdoptionRequestVO {

    /**
     * 发布公告的id也就是领养id
     */
    private Integer newsId;

    /**
     * 领养公告的标题
     */
    private String title;

    /**
     * 领养申请人id
     */
    private Integer userId;

    /**
     * 领养申请人昵称
     */
    private String userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 领养申请人电话号码
     */
    private String mobile;



}
