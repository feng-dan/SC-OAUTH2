package com.adrian.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * @author wangyunfei
 * @date 2017/6/9
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser extends SysAbstractAuditingEntity {
    private static final long serialVersionUID = 4514601540605095495L;
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户登录账号
     */
    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String userName;

    /**
     * 密码
     */
    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    /**
     * 显示昵称
     */
    @Size(max = 50)
    @Column(length = 50)
    private String displayName;

    /**
     * 邮箱
     */
    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    /**
     * 头像
     */
    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    /**
     * 启用
     */
    @Column(name = "status")
    private String status;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 角色
     */
    @JsonIgnore
    @ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<SysRole> roles = new HashSet<>();

    /**
     * 用户拥有权限的资源集合
     * cascade = CascadeType.ALL
     */
    @JsonIgnore
    @ManyToMany(targetEntity = SysUserResources.class, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<SysUserResources> userResources = new HashSet<>();

    /**
     * 权限
     */
    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> userAuthotities = new HashSet<>();
        for (SysRole sysRole : this.roles) {
            for (SysRoleResources sysRoleResources : sysRole.getRoleResources()) {
                userAuthotities.add(new SimpleGrantedAuthority(sysRoleResources.getValue()));
            }
        }
        return userAuthotities;
    }
}
