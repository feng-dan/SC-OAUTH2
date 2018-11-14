package com.adrian.repository;

import com.adrian.domain.SysUserResources;
import com.adrian.repository.support.WiselyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author asus
 * @ClassName SysUserRSRepository
 * @description TODO
 * @Date 2018/11/5 11:18
 * @Version 1.0v
 **/
@Repository
public interface SysUserRsRepository extends WiselyRepository<SysUserResources, Long> {
    /**
     * 获取所有父级资源
     *
     * @param pageable  分页参数
     * @param component 父级或者子级
     * @return Page<SysUserResources>
     */
    Page<SysUserResources> findAllByComponentContaining(Pageable pageable, String component);

    /**
     * @param pageable  分页参数
     * @param component 父级或者子级
     * @return Page<SysUserResources>
     */
    Page<SysUserResources> findAllByComponentNotContains(Pageable pageable, String component);
}
