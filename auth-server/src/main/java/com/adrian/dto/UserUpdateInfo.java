package com.adrian.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author asus
 * @ClassName UserUpdateInfo
 * @description TODO
 * @Date 2018/10/26 15:10
 * @Version 1.0v
 **/
public class UserUpdateInfo {

    @ApiModelProperty(value = "用户id")
    private Long uid;

    /**
     * 显示昵称
     */
    @ApiModelProperty(value = "用户名昵称")
    @NotBlank(message = "用户名昵称不能为空")
    private String firstName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "用户性别不能为空")
    private String lastName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    private String email;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @NotBlank(message = "用户头像不能为空")
    private String imageUrl;

}
