package com.adrian.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author asus
 * @ClassName UserUpdatePassword
 * @description TODO
 * @Date 2018/10/26 15:09
 * @Version 1.0v
 **/
@Data
public class UserUpdatePassword {

    private Long uid;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;


}
