package com.ch999.haha.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NeedSendAdoptionFeedBackVO {

    private Integer id;

    private String title;

    @JsonIgnore
    private Integer times;

    public NeedSendAdoptionFeedBackVO(Integer id, String title, Integer times) {
        this.id = id;
        this.title = title;
        this.times = times;
    }

    public String getTitle() {
        if(times > 0){
            title = title + " 第" +(5 - times) + "次反馈公告";
        }
        return title;
    }
}
