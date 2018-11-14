package com.adrian.repository;

import com.adrian.domain.SysUser;
import com.adrian.repository.support.WiselyRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户
 *
 * @author fengdan
 * @date 2018/10/9
 */
@Repository
public interface SysUserRepository extends WiselyRepository<SysUser, Long> {
    /**
     * 通过用户名查询，用户的角色/权限
     *
     * @param userName 用户名
     * @return SysUser
     */
    SysUser findOneWithRolesByUserName(String userName);

    /**
     * 通过用户名查询，用户信息
     *
     * @param userName 用户名
     * @return SysUser
     */
    SysUser findByUserName(String userName);

}

