/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.adrian.security;

import com.adrian.domain.SysRole;
import com.adrian.domain.SysUser;
import com.adrian.domain.SysUserResources;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fengdan
 * @date 2018/10/29
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String userName;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String status;
    private Set<SysRole> sysSysRoleList;
    @JsonIgnore
    private Set<SysUserResources> userResources = new HashSet<>();
    private String imageUrl;
    private String displayName;
    private String email;
    private String remark;
    private String address;
    private String phone;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(SysUser sysUser) {
        this.userId = sysUser.getId();
        this.userName = sysUser.getUserName();
        this.status = sysUser.getStatus();
        this.sysSysRoleList = sysUser.getRoles();
        this.imageUrl = sysUser.getImageUrl();
        this.displayName = sysUser.getDisplayName();
        this.email = sysUser.getEmail();
        this.password = sysUser.getPassword();
        this.remark = sysUser.getRemark();
        this.address = sysUser.getAddress();
        this.phone = sysUser.getPhone();
        this.userResources = sysUser.getUserResources();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        for (SysRole sysRole : this.sysSysRoleList) {

            //角色权限
            userAuthotities.add(new SimpleGrantedAuthority(sysRole.getValue()));

            //角色资源
            //for (SysRoleResources sysRoleResources : sysRole.getRoleResources()) {
            //    userAuthotities.add(new SimpleGrantedAuthority(sysRoleResources.getValue()));
            //}
            //用户资源
            for (SysUserResources sysUser : this.userResources) {
                userAuthotities.add(new SimpleGrantedAuthority(sysUser.getValue()));
            }
        }

        return userAuthotities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "1".equals(this.status);

    }

}
