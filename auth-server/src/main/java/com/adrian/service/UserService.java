package com.adrian.service;

import com.adrian.domain.SysUser;
import com.adrian.dto.LoginUserResources;

/**
 * @author asus
 * @ClassName UserService
 * @description TODO
 * @Date 2018/10/30 14:50
 * @Version 1.0v
 **/
public interface UserService {
    /**
     * 查询用户的资源
     *
     * @param username
     * @return
     */
    LoginUserResources findOneWithRolesByUsername(String username);

    /**
     * 通过用户查询用户信息
     *
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);

}
