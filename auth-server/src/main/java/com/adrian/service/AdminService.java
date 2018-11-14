package com.adrian.service;

import com.adrian.dto.UserRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 管理员服务
 *
 * @author asus
 * @ClassName AdminService
 * @description TODO
 * @Date 2018/10/25 16:52
 * @Version 1.0v
 **/
public interface AdminService {

    /**
     * 创建管理员
     *
     * @param adminInfo
     * @return
     */
    UserRegister createSysUser(UserRegister adminInfo);


    /**
     * 修改管理员
     *
     * @param adminInfo
     * @return
     */
    UserRegister update(UserRegister adminInfo);

    /**
     * 删除管理员
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 获取管理员详细信息
     *
     * @param id
     * @return
     */
    UserRegister getInfo(Long id);

    /**
     * 分页查询管理员
     *
     * @param username
     * @return
     */
    Page<UserRegister> query(String username, Pageable pageable);


}
