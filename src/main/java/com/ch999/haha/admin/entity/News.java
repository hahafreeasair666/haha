package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-01-22
 */
@TableName("news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 新闻id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 文章标题
     */
	private String title;
    /**
     * 文章内容（可能存html进去）
     */
	private String body;
    /**
     * 图片路径，取出来解析下
     */
	private String picture;
    /**
     * 作者id
     */
	private Integer createuserid;
    /**
     * 发布时间
     */
	private Date createtime;
    /**
     * 发布地点（存名字吧不然还得查表效率低）
     */
	private String createplace;
    /**
     * 最后编辑时间（发布时间以它为准）
     */
	private Date edittime;
    /**
     * 是否删除
     */
	private Boolean isdel;
    /**
     * 点赞数
     */
	private Integer zan;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Integer createuserid) {
		this.createuserid = createuserid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreateplace() {
		return createplace;
	}

	public void setCreateplace(String createplace) {
		this.createplace = createplace;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public Boolean isIsdel() {
		return isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public Integer getZan() {
		return zan;
	}

	public void setZan(Integer zan) {
		this.zan = zan;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "News{" +
			"id=" + id +
			", title=" + title +
			", body=" + body +
			", picture=" + picture +
			", createuserid=" + createuserid +
			", createtime=" + createtime +
			", createplace=" + createplace +
			", edittime=" + edittime +
			", isdel=" + isdel +
			", zan=" + zan +
			"}";
	}
}
