package com.adrian.service.impl;

import com.adrian.domain.SysUserResources;
import com.adrian.dto.RoleResources;
import com.adrian.dto.UserResources;
import com.adrian.repository.SysUserRsRepository;
import com.adrian.service.UserResourcesService;
import com.adrian.util.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户资源
 *
 * @author asus
 * @ClassName UserResourcesServiceImpl
 * @description TODO
 * @Date 2018/11/13 13:29
 * @Version 1.0v
 **/
@Log4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserResourcesServiceImpl implements UserResourcesService {

    @Autowired
    private SysUserRsRepository sysUserRsRepository;

    /**
     * 获取所有父级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return
     */
    @Override
    public PageImpl findAllPage(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<SysUserResources> home = sysUserRsRepository.findAllByComponentContaining(pageable, "Home");
        List<RoleResources> roleResources = new ArrayList<>();
        for (SysUserResources sysUserResources : home.getContent()) {
            RoleResources resources = new RoleResources();
            BeanUtils.copyProperties(sysUserResources, resources);
            roleResources.add(resources);
        }
        return new PageImpl<>(roleResources, pageable, home.getTotalElements());
    }

    /**
     * 获取所有子级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return
     */
    @Override
    public PageImpl findAllByComponentNotContains(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<SysUserResources> home = sysUserRsRepository.findAllByComponentNotContains(pageable, "Home");
        List<RoleResources> roleResources = new ArrayList<>();
        for (SysUserResources sysUserResources : home.getContent()) {
            RoleResources resources = new RoleResources();
            BeanUtils.copyProperties(sysUserResources, resources);
            roleResources.add(resources);
        }
        return new PageImpl<>(roleResources, pageable, home.getTotalElements());
    }

    /**
     * 新增父级资源
     *
     * @param userResources 用户资源dto
     * @return SysUserResources
     */
    @Override
    public SysUserResources saveFather(UserResources userResources) {
        //父级标识
        userResources.setComponent("Home");
        //默认启用此父级菜单
        userResources.setEnabled(true);
        SysUserResources sysUserResources = new SysUserResources();
        BeanUtils.copyProperties(userResources, sysUserResources);
        return sysUserRsRepository.save(sysUserResources);
    }

    /**
     * 新增子级资源
     *
     * @param userResources 用户资源dto
     * @return SysUserResources
     */
    @Override
    public SysUserResources saveChild(UserResources userResources) {
        String value = StringUtils.swapCase(StringUtils.getPinYin(userResources.getName()));
        //子级级标识
        userResources.setComponent(value);
        //默认启用此子级菜单
        userResources.setEnabled(true);
        //认证值
        userResources.setValue("/" + StringUtils.getPinYin(userResources.getName()));
        SysUserResources sysUserResources = new SysUserResources();
        BeanUtils.copyProperties(userResources, sysUserResources);
        return sysUserRsRepository.save(sysUserResources);
    }

    /**
     * 新增父级资源挂载子级资源
     *
     * @param parentId 父级资源id
     * @param childIds 子级资源id
     * @return boolean
     */
    @Override
    public boolean addParentMountedChildResources(Long parentId, String childIds) {
        childIds = org.apache.commons.lang.StringUtils.removeEnd(childIds, ",");
        //id查询父级资源
        SysUserResources sysUserResources = sysUserRsRepository.findOne(parentId);
        List<SysUserResources> children = new ArrayList<>();
        if (sysUserResources.getChildren().size() > 0) {
            //提前把之前的子级添加到现在的List<SysRoleResources>中
            children.addAll(sysUserResources.getChildren());
        }

        //添加到子级菜单中
        String[] resourceIdArray = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(childIds, ",");
        for (String resourceId : resourceIdArray) {
            //挂载新的子级菜单
            children.add(sysUserRsRepository.getOne(new Long(resourceId)));
        }
        SysUserResources userResources = sysUserRsRepository.save(new SysUserResources(sysUserResources.getId()
                , sysUserResources.getName()
                , sysUserResources.getValue()
                , sysUserResources.getPath()
                , sysUserResources.getIconCls()
                , sysUserResources.isEnabled()
                , sysUserResources.getResourceType()
                , sysUserResources.getComponent()
                , children
        ));
        return userResources.getChildren().size() > sysUserResources.getChildren().size();
    }

    /**
     * 删除父级下的子级资源
     *
     * @param parentId 父级资源id
     * @param childIds 子级资源id
     * @return boolean
     */
    @Override
    public boolean parentDelChildResources(Long parentId, String childIds) {
        childIds = org.apache.commons.lang.StringUtils.removeEnd(childIds, ",");
        String[] resourceIdArrays = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(childIds, ",");
        //parentId查询要删除的子级资源 sysUserResources
        SysUserResources sysUserResources = sysUserRsRepository.findOne(parentId);
        for (String resId : resourceIdArrays) {
            for (int j = 0; j < sysUserResources.getChildren().size(); j++) {
                if (("" + sysUserResources.getChildren().get(j).getId()).equals(resId)) {
                    sysUserResources.getChildren().remove(j);
                    j--;
                }
            }
        }
        SysUserResources save = sysUserRsRepository.save(sysUserResources);
        return save.getChildren().size() < sysUserResources.getChildren().size();
    }

    /**
     * parentId 查询单个父级资源
     *
     * @param parentId 父级资源id
     * @return SysRoleResources
     */
    @Override
    public SysUserResources findOne(Long parentId) {
        return sysUserRsRepository.findOne(parentId);
    }
}
