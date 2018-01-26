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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Animal{" +
			"id=" + id +
			", name=" + name +
			", body=" + body +
			", pic=" + pic +
			", type=" + type +
			"}";
	}
}
