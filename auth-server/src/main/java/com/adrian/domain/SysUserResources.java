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

    private String value;

    /**
     * 资源链接
     */
    private String path;
    /**
     * 图标
     */
    private String iconCls;

    private boolean enabled;

    private String component;

    /**
     * 资源类型
     */
    private String resourceType;

    @ManyToMany(targetEntity = SysUserResources.class, fetch = FetchType.EAGER)
    private List<SysUserResources> children;

}
