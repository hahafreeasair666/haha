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
@TableName("userfans")
public class UserFans extends Model<UserFans> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 关注者id
     */
	private Integer userid1;
    /**
     * 被关注者id
     */
	private Integer userid2;
    /**
     * 是否关注着
     */
	private Boolean isdel;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid1() {
		return userid1;
	}

	public void setUserid1(Integer userid1) {
		this.userid1 = userid1;
	}

	public Integer getUserid2() {
		return userid2;
	}

	public void setUserid2(Integer userid2) {
		this.userid2 = userid2;
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
		return "UserFans{" +
			"id=" + id +
			", userid1=" + userid1 +
			", userid2=" + userid2 +
			", isdel=" + isdel +
			"}";
	}
}
