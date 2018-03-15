package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
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
 * @author haha
 * @since 2018-01-25
 */
@Setter
@Getter
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
    @TableField("createuserid")
	private Integer createUserId;

    /**
     * 发布时间
     */
    @TableField("createtime")
	private Date createTime;

    /**
     * 发布地点（存名字吧不然还得查表效率低）
     */
    @TableField("createplace")
	private String createPlace;

    /**
     * 最后编辑时间（发布时间以它为准）
     */
    @TableField("edittime")
	private Date editTime;

    /**
     * 是否删除
     */
    @TableField("isdel")
    @TableLogic
	private Boolean isDel;

    public Boolean getDel() {
        return isDel;
    }

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

    /**
     * 是否是收养信息
     */
    @TableField("isadoptionnews")
	private Boolean isAdoptionNews;

    public Boolean getAdoptionNews() {
        return isAdoptionNews;
    }

    /**
     * 领养动物的id
     */
    @TableField("parentid")
    private Integer parentId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
