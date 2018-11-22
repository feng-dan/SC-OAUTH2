package com.adrian.service;

import com.adrian.domain.SysRoleResources;
import org.springframework.data.domain.PageImpl;

/**
 * 角色资源
 *
 * @author asus
 * @ClassName RoleResourcesService
 * @description TODO
 * @Date 2018/11/5 15:40
 * @Version 1.0v
 **/
public interface RoleResourcesService {

    /**
     * 获取所有父级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return
     */
    PageImpl findAllByComponentContaining(Integer page, Integer size);


    /**
     * 获取所有子级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return
     */
    PageImpl findAllByComponentNotContains(Integer page, Integer size);

    /**
     * 新增父级资源
     *
     * @param roleResources 角色资源dto
     * @return SysRoleResources
     */
    SysRoleResources addParentResource(com.adrian.dto.RoleResources roleResources);

    /**
     * 新增子级资源
     *
     * @param roleResources 角色资源dto
     * @return SysRoleResources
     */
    SysRoleResources addChildResources(com.adrian.dto.RoleResources roleResources);


    /**
     * 新增父级资源挂载子级资源
     *
     * @param parentId 父级资源id
     * @param childIds
     * @return boolean
     */
    boolean addParentMountedChildResources(Long parentId, String childIds);

    /**
     * 删除父级下的子级资源
     *
     * @param parentId 父级资源id
     * @param childIds 子级资源id
     * @return boolean
     */
    boolean parentDelChildResources(Long parentId, String childIds);

    /**
     * parentId 查询单个父级资源
     *
     * @param parentId 父级资源id
     * @return SysRoleResources
     */
    SysRoleResources findOne(Long parentId);


}
