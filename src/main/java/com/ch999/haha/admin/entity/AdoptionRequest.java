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
 * @since 2018-03-15
 */
@Data
@NoArgsConstructor
@TableName("AdoptionRequest")
public class AdoptionRequest extends Model<AdoptionRequest> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联news表主键
     */
    @TableField("newsid")
    private Integer newsId;
    /**
     * 想领养人的id
     */
    @TableField("userid")
    private Integer userId;
    /**
     * 是否成功领养
     */
    @TableField("issuccess")
    private Boolean isSuccess;
    /**
     * 是否删除，就是未收养成功后可以取消收养请求
     */
    @TableField("isdel")
    @TableLogic
    private Boolean isDel;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public Boolean getDel() {
        return isDel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public AdoptionRequest(Integer newsId, Integer userId) {
        this.newsId = newsId;
        this.userId = userId;
        this.isDel = false;
    }
}
