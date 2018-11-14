package com.adrian.service.impl;

import com.adrian.domain.SysRoleResources;
import com.adrian.domain.SysRole;
import com.adrian.dto.RoleInfo;
import com.adrian.repository.SysAuthotityRepository;
import com.adrian.repository.SysRoleRepository;
import com.adrian.repository.support.QueryResultConverter;
import com.adrian.service.RoleService;
import com.adrian.util.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色
 * @author asus
 * @ClassName RoleServiceImpl
 * @description TODO
 * @Date 2018/10/26 16:16
 * @Version 1.0v
 **/

@Log4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysAuthotityRepository sysAuthotityRepository;

    /**
     * 创建角色
     *
     * @param roleInfo 角色dto
     * @return RoleInfo
     */
    @Override
    public RoleInfo create(RoleInfo roleInfo) {
        SysRole sysRole = new SysRole();
        String value = StringUtils.swapCase(StringUtils.getPinYin(roleInfo.getName()));
        roleInfo.setValue("ROLE_" + value);
        BeanUtils.copyProperties(roleInfo, sysRole);
        roleInfo.setId(sysRoleRepository.save(sysRole).getId());
        return roleInfo;
    }

    /**
     * 修改角色
     *
     * @param roleInfo 角色dto
     * @return RoleInfo
     */
    @Override
    public RoleInfo update(RoleInfo roleInfo) {
        String value = StringUtils.swapCase(StringUtils.getPinYin(roleInfo.getName()));
        roleInfo.setValue("ROLE_" + value);
        SysRole sysRole = sysRoleRepository.findOne(roleInfo.getId());
        BeanUtils.copyProperties(roleInfo, sysRole);
        return roleInfo;
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return boolean
     */
    @Override
    public boolean delete(Long id) {
        try {
            sysRoleRepository.delete(id);
            return true;
        } catch (RuntimeException e) {
            log.error("异常:{}" + e.getMessage());
            throw new RuntimeException("不能删除拥有此角色的用户！");
        }
    }

    /**
     * 获取角色详细信息
     *
     * @param id 角色id
     * @return RoleInfo
     */
    @Override
    public RoleInfo getInfo(Long id) {
        SysRole sysRole = sysRoleRepository.findOne(id);
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(sysRole, info);
        return info;
    }

    /**
     * 查询所有角色
     *
     * @return List<RoleInfo>
     */
    @Override
    public List<RoleInfo> findAll() {
        return QueryResultConverter.convert(sysRoleRepository.findAll(), RoleInfo.class);
    }

    /**
     * 通过角色id获取该角色的所有资源
     *
     * @param id 角色id
     * @return List<SysRoleResources>
     */
    @Override
    public List<SysRoleResources> getRoleResources(Long id) {
        SysRole sysRole = sysRoleRepository.findOne(id);
        List<SysRoleResources> authorities = new ArrayList<>();
        for (SysRoleResources sysRoleResources : sysRole.getRoleResources()) {
            SysRoleResources authority = new SysRoleResources();
            authority.setId(sysRoleResources.getId());
            authority.setValue(sysRoleResources.getValue());
            authority.setName(sysRoleResources.getName());
            authority.setChildren(sysRoleResources.getChildren());
            authority.setIconCls(sysRoleResources.getIconCls());
            authority.setPath(sysRoleResources.getPath());
            authority.setEnabled(sysRoleResources.isEnabled());
            authority.setResourceType(sysRoleResources.getResourceType());
            authorities.add(authority);
        }
        return authorities;
    }


    /**
     * 给某个角色添加资源
     *
     * @param roleId      角色id
     * @param resourceIds 资源id
     * @return boolean
     */
    @Override
    public boolean setRoleResources(Long roleId, String resourceIds) {
        Set<SysRoleResources> sysAuthorities = new HashSet<>();
        resourceIds = org.apache.commons.lang.StringUtils.removeEnd(resourceIds, ",");
        SysRole sysRole = sysRoleRepository.findOne(roleId);
        String[] resourceIdArray = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
        // 循环的格式for（循环变量类型   变量名：变量对象）
        for (String resourceId : resourceIdArray) {
            sysAuthorities.add(sysAuthotityRepository.getOne(new Long(resourceId)));
        }
        //角色添加资源
        SysRole save = sysRoleRepository.save(new SysRole(sysRole.getId(), sysRole.getName(), sysRole.getValue(), sysAuthorities));
        if (save != null && !"".equals(save.getRoleResources())) {
            return true;
        }
        return false;
    }
}
