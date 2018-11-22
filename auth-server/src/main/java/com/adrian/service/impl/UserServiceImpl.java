package com.adrian.service.impl;

import com.adrian.domain.SysRole;
import com.adrian.domain.SysUser;
import com.adrian.domain.SysUserResources;
import com.adrian.dto.LoginUserResources;
import com.adrian.repository.SysRoleRepository;
import com.adrian.repository.SysUserRepository;
import com.adrian.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 用户
 * @author asus
 * @ClassName UserServiceImpl
 * @description TODO
 * @Date 2018/11/1 16:16
 * @Version 1.0v
 **/
@Log4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final SysRoleRepository sysRoleRepository;

    private final SysUserRepository sysUserRepository;

    @Autowired
    public UserServiceImpl(SysRoleRepository sysRoleRepository, SysUserRepository sysUserRepository) {
        this.sysRoleRepository = sysRoleRepository;
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * 查询用户的资源
     *
     * @param username
     * @return
     */
    @Override
    public LoginUserResources findOneWithRolesByUsername(String username) {
        LoginUserResources loginUserResources = new LoginUserResources();
        SysUser oneWithRolesByUsername = sysUserRepository.findOneWithRolesByUserName(username);
        Set<SysRole> sysSysSysRoles = oneWithRolesByUsername.getRoles();
        for (SysRole sysRole : sysSysSysRoles) {
            SysRole oneWithAuthoritiesById = sysRoleRepository.findOneWithAuthoritiesById(sysRole.getId());
            BeanUtils.copyProperties(oneWithAuthoritiesById, loginUserResources);
        }

        Set<SysUserResources> userResources = oneWithRolesByUsername.getUserResources();
        for (SysUserResources sysUserResources : userResources) {
            SysUser oneWithuserResourcesById = sysUserRepository.findOne(sysUserResources.getId());
            BeanUtils.copyProperties(oneWithuserResourcesById, loginUserResources);
        }

        return loginUserResources;
    }

    /**
     * 通过用户查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser findByUserName(String userName) {
        return sysUserRepository.findByUserName(userName);
    }

}
