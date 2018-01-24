package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @since 2018-01-22
 */
@Getter
@Setter
@ToString
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

	/**
	 * 用于区分是猫类还是狗类的，狗1，猫2
	 */
	@TableField("animaltype")
	private Integer animalType;

	/**
	 * 具体猫狗种类的id
	 */
	@TableField("dogorcattype")
	private Integer dogOrCatType;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
