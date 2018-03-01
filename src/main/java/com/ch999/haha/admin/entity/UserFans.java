package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
    @TableField("userid1")
	private Integer userId1;
    /**
     * 被关注者id
     */
    @TableField("userid2")
	private Integer userId2;
    /**
     * 是否关注着
     */
    @TableField("isdel")
	private Boolean isDel;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
