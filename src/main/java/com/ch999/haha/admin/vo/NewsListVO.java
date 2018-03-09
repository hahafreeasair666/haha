package com.ch999.haha.admin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewsListVO {

    private Integer id;

    private String title;

    private String imgPath;

    private Date createTime;

    private String createUser;

    private String place;
}
