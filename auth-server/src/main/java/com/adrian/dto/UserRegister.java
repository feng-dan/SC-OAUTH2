package com.adrian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister implements Serializable {

    private static final long serialVersionUID = -7481073543895432460L;

    private Long uid;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度至少6位")
    private String password;


}
