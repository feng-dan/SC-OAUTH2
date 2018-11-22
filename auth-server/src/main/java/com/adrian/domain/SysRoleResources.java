package com.adrian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 角色资源
 *
 * @author fengdan
 * @date 2018/10/9
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleResources extends SysAbstractAuditingEntity {
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
     * 资源类型
     */
    private String resourceType;
    /**
     * 表示父级菜单或者子级菜单
     */
    private String component;

    @OneToMany(targetEntity = SysRoleResources.class, fetch = FetchType.EAGER)
    private List<SysRoleResources> children;
}
