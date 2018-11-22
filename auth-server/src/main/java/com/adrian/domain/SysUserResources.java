package com.adrian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 用户资源
 *
 * @author fengdan
 * @date 2018/10/9
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SysUserResources extends SysAbstractAuditingEntity {
    private static final long serialVersionUID = 8440697408645271160L;
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 资源名称，如xx菜单，xx按钮
     */
    private String name;
    /**
     * 权限标识
     */
    private String value;

    /**
     * 资源链接
     */
    private String path;
    /**
     * 图标
     */
    private String iconCls;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 表示父级菜单或者子级菜单
     */
    private String component;

    /**
     * 资源类型
     */
    private String resourceType;

    @OneToMany(targetEntity = SysUserResources.class, fetch = FetchType.EAGER)
    private List<SysUserResources> children;

}
