package com.adrian.service.impl;

import com.adrian.domain.SysRoleResources;
import com.adrian.dto.UserResources;
import com.adrian.repository.SysRoleRsRepository;
import com.adrian.service.RoleResourcesService;
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
 * 角色资源
 *
 * @author asus
 * @ClassName RoleResourcesServiceImpl
 * @description TODO
 * @Date 2018/11/5 15:42
 * @Version 1.0v
 **/
@Log4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleResourcesServiceImpl implements RoleResourcesService {

    private final SysRoleRsRepository sysRoleRsRepository;

    @Autowired
    public RoleResourcesServiceImpl(SysRoleRsRepository sysRoleRsRepository) {
        this.sysRoleRsRepository = sysRoleRsRepository;
    }


    /**
     * 获取所有父级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return Page<SysRoleResources>
     */
    @Override
    public PageImpl findAllByComponentContaining(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<SysRoleResources> all = sysRoleRsRepository.findAllByComponentContaining(pageable, "Home");
        List<UserResources> userResources = new ArrayList<>();
        for (SysRoleResources sysRoleResources : all.getContent()) {
            UserResources resources = new UserResources();
            BeanUtils.copyProperties(sysRoleResources, resources);
            userResources.add(resources);
        }
        return new PageImpl<>(userResources, pageable, all.getTotalElements());
    }


    /**
     * 获取所有子级资源
     *
     * @param page 当前页
     * @param size 条数
     * @return Page<SysRoleResources>
     */
    @Override
    public PageImpl findAllByComponentNotContains(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<SysRoleResources> home = sysRoleRsRepository.findAllByComponentNotContains(pageable, "Home");
        List<com.adrian.dto.RoleResources> roleResources = new ArrayList<>();
        for (SysRoleResources sysRoleResources : home.getContent()) {
            com.adrian.dto.RoleResources resources = new com.adrian.dto.RoleResources();
            BeanUtils.copyProperties(sysRoleResources, resources);
            roleResources.add(resources);
        }
        return new PageImpl<>(roleResources, pageable, home.getTotalElements());
    }

    /**
     * 新增父级资源
     *
     * @param roleResources 角色资源dto
     * @return SysRoleResources
     */
    @Override
    public SysRoleResources addParentResource(com.adrian.dto.RoleResources roleResources) {
        //父级标识
        roleResources.setComponent("Home");
        //默认启用此父级菜单
        roleResources.setEnabled(true);
        String pinYin = StringUtils.getPinYin(roleResources.getName());
        roleResources.setPath("/" + pinYin);
        String value = StringUtils.swapCase(StringUtils.getPinYin(pinYin));
        roleResources.setValue(value);
        SysRoleResources sysRoleResources = new SysRoleResources();
        BeanUtils.copyProperties(roleResources, sysRoleResources);
        return sysRoleRsRepository.save(sysRoleResources);
    }

    /**
     * 新增子级资源
     *
     * @param roleResources 角色资源dto
     * @return SysRoleResources
     */
    @Override
    public SysRoleResources addChildResources(com.adrian.dto.RoleResources roleResources) {
        String value = StringUtils.swapCase(StringUtils.getPinYin(roleResources.getName()));
        //子级资源标识
        roleResources.setComponent(value);
        //默认启用此子级菜单
        roleResources.setEnabled(true);
        //权限标识
        roleResources.setValue(value);
        //路径
        String pinYin = StringUtils.getPinYin(roleResources.getName());
        roleResources.setPath("/" + pinYin);
        SysRoleResources sysRoleResources = new SysRoleResources();
        BeanUtils.copyProperties(roleResources, sysRoleResources);
        return sysRoleRsRepository.save(sysRoleResources);
    }

    /**
     * 新增父级资源挂载子级资源
     *
     * @param parentId 父级资源id
     * @param childIds
     * @return boolean
     */
    @Override
    public boolean addParentMountedChildResources(Long parentId, String childIds) {
        childIds = org.apache.commons.lang.StringUtils.removeEnd(childIds, ",");

        //id查询父级资源
        SysRoleResources sysRoleRsRepositoryOne = sysRoleRsRepository.findOne(parentId);
        //存储保存前的list大小
        int size = sysRoleRsRepositoryOne.getChildren().size();
        List<SysRoleResources> children = new ArrayList<>();

        if (sysRoleRsRepositoryOne.getChildren().size() > 0) {
            //提前把之前的子级添加到现在的List<SysRoleResources>中
            children.addAll(sysRoleRsRepositoryOne.getChildren());
        }

        //添加到子级菜单中
        String[] resourceIdArray = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(childIds, ",");
        for (String resourceId : resourceIdArray) {
            //挂载新的子级菜单
            children.add(sysRoleRsRepository.getOne(new Long(resourceId)));
        }
        SysRoleResources sysRoleResources = sysRoleRsRepository.save(new SysRoleResources(sysRoleRsRepositoryOne.getId()
                , sysRoleRsRepositoryOne.getName()
                , sysRoleRsRepositoryOne.getValue()
                , sysRoleRsRepositoryOne.getPath()
                , sysRoleRsRepositoryOne.getIconCls()
                , sysRoleRsRepositoryOne.isEnabled()
                , sysRoleRsRepositoryOne.getResourceType()
                , sysRoleRsRepositoryOne.getComponent()
                , children
        ));
        return sysRoleResources.getChildren().size() > size;
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
        String[] resourceIdArray = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(childIds, ",");
        //parentId查询要删除的子级资源 one
        SysRoleResources one = sysRoleRsRepository.findOne(parentId);
        int sized = one.getChildren().size();
        for (String resourceId : resourceIdArray) {
            for (int i = 0; i < one.getChildren().size(); i++) {
                if ((one.getChildren().get(i).getId() + "").equals(resourceId)) {
                    one.getChildren().remove(i);
                    i--;
                }
            }
        }
        SysRoleResources save = sysRoleRsRepository.save(one);
        return save.getChildren().size() <= sized;
    }

    /**
     * parentId 查询单个父级资源
     *
     * @param parentId 父级资源id
     * @return SysRoleResources
     */
    @Override
    public SysRoleResources findOne(Long parentId) {
        return sysRoleRsRepository.findOne(parentId);
    }
}
