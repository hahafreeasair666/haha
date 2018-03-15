package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UserInfoUpdateVO {

    /**
     * 修改头像
     */
    private MultipartFile file;

    /**
     * 修改用户名
     */
    private String name;

    /**
     * 个人描述
     */
    private String description;

    /**
     * 修改密码的老密码
     */
    private String oldPwd;

    /**
     * 修改密码的新密码
     */
    private String newPwd1;

    private String newPwd2;

    /**
     * 要修改为的的手机号码
     */
    private String mobile;


}
