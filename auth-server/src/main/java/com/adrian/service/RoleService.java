/**
 *
 */
package com.adrian.service;


import com.adrian.domain.SysRoleResources;
import com.adrian.dto.RoleInfo;

import java.util.List;

/**
 * 角色服务
 *
 * @author zhailiang
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param roleInfo 角色dto
     * @return RoleInfo
     */
    RoleInfo create(RoleInfo roleInfo);

    /**
     * 修改角色
     *
     * @param roleInfo 角色dto
     * @return RoleInfo
     */
    RoleInfo update(RoleInfo roleInfo);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return boolean
     */
    boolean delete(Long id);

    /**
     * 获取角色详细信息
     *
     * @param id 角色id
     * @return RoleInfo
     */
    RoleInfo getInfo(Long id);

    /**
     * 查询所有角色
     *
     * @return List<RoleInfo>
     */
    List<RoleInfo> findAll();

    /**
     * 通过角色id获取该角色的所有资源
     *
     * @param id 角色id
     * @return List<SysRoleResources>
     */
    List<SysRoleResources> getRoleResources(Long id);

    /**
     * 给某个角色添加资源
     *
     * @param roleId      角色id
     * @param resourceIds 资源id
     * @return boolean
     */
    boolean setRoleResources(Long roleId, String resourceIds);

}
