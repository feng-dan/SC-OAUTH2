package com.adrian.repository;

import com.adrian.domain.SysRoleResources;
import com.adrian.repository.support.WiselyRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限
 *
 * @author fengdan
 * @date 2018/10/14
 */
@Repository
public interface SysAuthotityRepository extends WiselyRepository<SysRoleResources, Long> {
}
