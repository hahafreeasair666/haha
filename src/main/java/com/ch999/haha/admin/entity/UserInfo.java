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
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-01-25
 */
@TableName("userinfo")
@Getter
@Setter
@ToString
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键账号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户名
     */
	private String username;
    /**
     * 密码
     */
	private String pwd;
    /**
     * 手机号
     */
	private String mobile;
	/**
	 * 个性签名
	 */
	private String autograph;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 *用户头像
	 */
    @TableField("picpath")
	private String picPath;
    /**
     * 微信授权的openid
     */
	private Integer openid;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
