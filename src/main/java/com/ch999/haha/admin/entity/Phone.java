package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-13
 */
@Data
public class Phone extends Model<Phone> {

    public static final String CK = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[6])|(18[0,5-9])|(19[9]))\\d{8}$";

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("phone_num")
    private String phoneNum;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Phone(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
