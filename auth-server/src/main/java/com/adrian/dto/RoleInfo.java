/**
 *
 */
package com.adrian.dto;

import com.adrian.domain.SysRoleResources;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhailiang
 */

@Data
public class RoleInfo {

    private Long id;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名不能为空")
    private String name;

    /**
     * 角色值 ROLE_
     */
    private String value;

    /**
     * 权限
     */
    private Set<SysRoleResources> roleResources = new HashSet<>();

}
