package com.adrian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 * @author wangyunfei
 * @date 2017/6/9
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SysRole extends SysAbstractAuditingEntity {

    private static final long serialVersionUID = -8039203807217267179L;
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色值 ROLE_
     */
    private String value;
    /**
     * 角色拥有权限的资源集合
     * cascade = CascadeType.ALL
     */
    //@JsonIgnore
    @ManyToMany(targetEntity = SysRoleResources.class, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<SysRoleResources> roleResources = new HashSet<>();

}
