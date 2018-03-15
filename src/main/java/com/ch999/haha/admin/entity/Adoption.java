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
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-14
 */
@Data
@NoArgsConstructor
@TableName("adoption")
public class Adoption extends Model<Adoption> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 动物id吧可以理解为，对应news表id
     */
    @TableField("adoptionid")
    private Integer adoptionId;
    /**
     * 对应收养公告的标题
     */
    @TableField("newstitle")
    private String newsTitle;
    /**
     * 是否被领养
     */
    @TableField("isadoption")
    private Boolean isAdoption;
    /**
     * 领养人id
     */
    @TableField("userid")
    private Integer userId;
    /**
     * 是否删除
     */
    @TableField("isdel")
    @TableLogic
    private Boolean isDel;
    /**
     * 领养时间，未领养的时间为空
     */
    @TableField("adoptiontime")
    private Date adoptionTime;

    public Boolean getDel() {
        return isDel;
    }

    public Boolean getAdoption() {
        return isAdoption;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Adoption(Integer adoptionId, String newsTitle) {
        this.adoptionId = adoptionId;
        this.newsTitle = newsTitle;
    }
}
