package com.ch999.haha.admin.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyAdoptionVO {

    /**
     * 收养的id
     */
    private Integer newsId;

    /**
     * 收养的标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 是否成功收养
     */
    private Integer isSuccess;
}
