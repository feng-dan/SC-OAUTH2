///**
// *
// */
//package com.adrian.common.init;
//
//import com.adrian.domain.SysRoleResources;
//import com.adrian.domain.SysRole;
//import com.adrian.domain.SysUser;
//import com.adrian.repository.SysAuthotityRepository;
//import com.adrian.repository.SysRoleRepository;
//import com.adrian.repository.SysUserRepository;
//import lombok.extern.log4j.Log4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 默认的系统数据初始化器，永远在其他数据初始化器之前执行
// *
// * @author zhailiang
// */
//@Log4j
//@Component
//public class AdminDataInitializer extends AbstractDataInitializer {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private SysRoleRepository sysRoleRepository;
//
//    @Autowired
//    private SysUserRepository sysUserRepository;
//
//    @Autowired
//    private SysAuthotityRepository sysAuthotityRepository;
//
//
//    @Override
//    public Integer getIndex() {
//        return Integer.MIN_VALUE;
//    }
//
//
//    @Override
//    protected void doInit() {
//        sysAuthority();
//        SysRole role = initRole();
//        initAdmin(role);
//    }
//
//    /**
//     * 初始化用户数据
//     *
//     * @param role
//     */
//    private void initAdmin(SysRole role) {
//        SysUser sysUser = new SysUser();
//        sysUser.setUsername("admin");
//        sysUser.setPassword(passwordEncoder.encode("123456"));
//        sysUserRepository.save(sysUser);
//        //
//        //RoleAdmin roleAdmin = new RoleAdmin();
//        //roleAdmin.setRole(role);
//        //roleAdmin.setAdmin(admin);
//        //roleAdminRepository.save(roleAdmin);
//    }
//
//    /**
//     * 初始化角色数据
//     *
//     * @return role
//     */
//    private SysRole initRole() {
//        SysRole role = new SysRole();
//        role.setName("超级管理员");
//        role.setValue("ROLE_ADMIN");
//        sysRoleRepository.save(role);
//        return role;
//    }
//
//    /**
//     * 初始化权限数据
//     */
//    protected void sysAuthority() {
//        SysRoleResources root = createRoot("管理员");
//        log.info("初始化权限数据" + root);
//    }
//
//    @Override
//    protected boolean isNeedInit() {
//        return sysUserRepository.count() == 0;
//    }
//
//    /**
//     * @param name
//     * @return
//     */
//    protected SysRoleResources createRoot(String name) {
//        SysRoleResources sysAuthority = new SysRoleResources();
//        sysAuthority.setName(name);
//        sysAuthority.setValue("authority-admin");
//        sysAuthotityRepository.save(sysAuthority);
//        return sysAuthority;
//    }
//
//}
