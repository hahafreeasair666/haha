package com.ch999.haha.admin.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RegisterVO {

    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "手机号不能为空")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String pwd1;

    @NotNull(message = "确认密码不能为空")
    @NotBlank(message = "确认密码不能为空")
    private String pwd2;
}
