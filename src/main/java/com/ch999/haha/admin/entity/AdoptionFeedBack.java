package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-17
 */
@Data
@NoArgsConstructor
@TableName("adoptionfeedback")
public class AdoptionFeedBack extends Model<AdoptionFeedBack> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("adoptionid")
    private Integer adoptionId;

    @TableField("newsid")
    private Integer newsId;

    /**
     * 该反馈公告上次定时任务获得的赞
     */
    @TableField("lasttimezan")
    private Integer lastTimeZan;

    /**
     * 是否删除
     */
    @TableField("isdel")
    @TableLogic
    private Boolean isDel;

    public Boolean getIsdel() {
        return isDel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public AdoptionFeedBack(Integer adoptionId, Integer newsId) {
        this.adoptionId = adoptionId;
        this.newsId = newsId;
    }
}
