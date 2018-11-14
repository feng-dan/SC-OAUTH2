package com.adrian.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author asus
 * @ClassName UserRegister
 * @description TODO
 * @Date 2018/10/25 16:53
 * @Version 1.0v
 **/
@Data
public class UserRegister implements Serializable {

    private static final long serialVersionUID = -7481073543895432460L;

    private Long uid;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
