package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
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
	private Integer userid;
    /**
     * 头条id
     */
	private Integer newid;
    /**
     * 是否收藏
     */
	private Boolean isdel;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getNewid() {
		return newid;
	}

	public void setNewid(Integer newid) {
		this.newid = newid;
	}

	public Boolean isIsdel() {
		return isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "NewsCollections{" +
			"id=" + id +
			", userid=" + userid +
			", newid=" + newid +
			", isdel=" + isdel +
			"}";
	}
}
