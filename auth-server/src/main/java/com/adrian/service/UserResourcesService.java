package com.adrian.service;

import com.adrian.domain.SysUserResources;
import com.adrian.dto.UserResources;
import org.springframework.data.domain.PageImpl;

/**
 * 用户资源
 *
 * @author asus
 * @ClassName UserResourcesService
 * @description TODO
 * @Date 2018/11/5 15:40
 * @Version 1.0v
 **/
public interface UserResourcesService {

    /**
     * 获取所有父级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return
     */
    PageImpl findAllPage(Integer page, Integer size);


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
     * @param userResources 用户资源dto
     * @return SysUserResources
     */
    SysUserResources saveFather(UserResources userResources);


    /**
     * 新增子级资源
     *
     * @param userResources 用户资源dto
     * @return SysUserResources
     */
    SysUserResources saveChild(UserResources userResources);


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
    SysUserResources findOne(Long parentId);

}
