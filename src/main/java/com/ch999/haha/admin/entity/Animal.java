package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
@Getter
@Setter
@ToString
@TableName("animal")
public class Animal extends Model<Animal> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键，猫狗id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 名字
     */
	private String name;
    /**
     * 简介
     */
	private String body;
    /**
     * 图片路径
     */
	private String pic;
    /**
     * 种类狗1，猫2
     */
	private Integer type;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
