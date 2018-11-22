package com.adrian.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author asus
 * @ClassName Admin
 * @description TODO
 * @Date 2018/11/21 15:56
 * @Version 1.0v
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Admin extends SysAbstractAuditingEntity {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 管理员登录账号
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
     * 权限
     */
    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();

}
