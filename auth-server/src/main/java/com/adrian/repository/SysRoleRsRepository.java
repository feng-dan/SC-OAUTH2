package com.adrian.repository;

import com.adrian.domain.SysRoleResources;
import com.adrian.repository.support.WiselyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 角色资源
 *
 * @author asus
 * @ClassName SysRoleRsRepository
 * @description TODO
 * @Date 2018/11/5 15:37
 * @Version 1.0v
 **/
@Repository
public interface SysRoleRsRepository extends WiselyRepository<SysRoleResources, Long>, JpaSpecificationExecutor<SysRoleResources> {

    /**
     * 获取所有父级资源
     *
     * @param pageable  分页参数
     * @param component 父级或者子级
     * @return Page<SysRoleResources>
     */
    Page<SysRoleResources> findAllByComponentContaining(Pageable pageable, String component);

    /**
     * @param pageable  分页参数
     * @param component 父级或者子级
     * @return Page<SysRoleResources>
     */
    Page<SysRoleResources> findAllByComponentNotContains(Pageable pageable, String component);


}

