package com.ch999.haha.admin.vo.mappervo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AdoptionSuccessNewsVO {

    private Integer pid;

    private String title;

    private Integer newId;

    private String newTitle;

    private Date createTime;

    private Date time1;

    private Date time2;

    private Date time3;

    private Date time4;
}
