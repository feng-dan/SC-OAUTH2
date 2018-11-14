package com.adrian.service.impl;

import com.adrian.domain.SysUser;
import com.adrian.dto.UserRegister;
import com.adrian.repository.SysUserRepository;
import com.adrian.service.AdminService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员
 * @author asus
 * @ClassName AdminServiceImpl
 * @description TODO
 * @Date 2018/10/25 16:57
 * @Version 1.0v
 **/
@Log4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRepository sysUserRepository;

    /**
     * @param adminInfo
     * @return
     */
    @Override
    public UserRegister createSysUser(UserRegister adminInfo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(adminInfo, sysUser);
        sysUser.setPassword(passwordEncoder.encode(adminInfo.getPassword()));
        sysUserRepository.save(sysUser);
        return adminInfo;
    }

    /**
     * 修改管理员
     *
     * @param adminInfo
     * @return
     */
    @Override
    public UserRegister update(UserRegister adminInfo) {
        return null;
    }

    /**
     * 删除管理员
     *
     * @param id
     */
    @Override
    public void delete(Long id) {

    }

    /**
     * 获取管理员详细信息
     *
     * @param id
     * @return
     */
    @Override
    public UserRegister getInfo(Long id) {
        return null;
    }

    /**
     * 分页查询管理员
     *
     * @param username
     * @param pageable
     * @return
     */
    @Override
    public Page<UserRegister> query(String username, Pageable pageable) {
        return null;
    }
}
