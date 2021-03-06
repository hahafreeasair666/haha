package com.ch999.haha.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
@Data
@NoArgsConstructor
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
    @JsonIgnore
	private String pwd;
    /**
     * 手机号
     */
    @JsonIgnore
	private String mobile;
	/**
	 * 个性签名
	 */
	@JsonIgnore
	private String autograph;
	/**
	 * 生日
	 */
	@JsonIgnore
	private Date birthday;
	/**
	 *用户头像
	 */
    @TableField("picpath")
	private String picPath;
    /**
     * 微信授权的openid
     */
    @JsonIgnore
	private Integer openid;
	/**.
	 * 用户是否被删除
	 */
	@TableField("isdel")
	@TableLogic
	@JsonIgnore
	private Boolean isDel;

	@JsonIgnore
	public Boolean getDel() {
		return isDel;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getPicPath() {
		if(picPath == null){
			picPath = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2376638784,1470858270&fm=27&gp=0.jpg";
		}
		return picPath;
	}

	public UserInfo(Integer id) {
		this.id = id;
	}
}
