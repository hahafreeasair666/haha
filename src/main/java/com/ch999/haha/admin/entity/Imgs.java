package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
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
 * @since 2018-03-07
 */
@Data
public class Imgs extends Model<Imgs> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("img_name")
	private String imgName;
	@TableField("img_url")
	private String imgUrl;
	@TableLogic
	private Integer isdel;

	public Integer getIsdel() {
		return isdel;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
