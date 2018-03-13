package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@ToString
@Setter
@Getter
@NoArgsConstructor
@TableName("newscollections")
public class NewsCollections extends Model<NewsCollections> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 收藏者id
     */
    @TableField("userid")
	private Integer userId;
    /**
     * 头条id
     */
    @TableField("newid")
	private Integer newId;
    /**
     * 是否收藏
     */
    @TableField("isdel")
	@TableLogic
	private Boolean isDel;

	public Boolean getDel() {
		return isDel;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public NewsCollections(Integer userId, Integer newId) {
		this.userId = userId;
		this.newId = newId;
		this.isDel = false;
	}
}
