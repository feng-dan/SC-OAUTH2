package com.adrian.repository;

import com.adrian.domain.SysRole;
import com.adrian.repository.support.WiselyRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色
 *
 * @author fengdan
 * @date 2018/10/9
 */
@Repository
public interface SysRoleRepository extends WiselyRepository<SysRole, Long>, CrudRepository<SysRole, Long> {
    /**
     * 查询
     *
     * @param id id
     * @return SysRole
     */
    SysRole findOneWithAuthoritiesById(Long id);

    /**
     *  角色名称查询该角色的信息（判断角色是否存在）
     * @param roleName
     * @return
     */
    SysRole findSysRoleByName(String roleName);
}
