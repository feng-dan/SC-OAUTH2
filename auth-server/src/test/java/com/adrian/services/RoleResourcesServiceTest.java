package com.adrian.services;

import com.adrian.AuthServerApplicationTests;
import com.adrian.domain.SysRoleResources;
import com.adrian.dto.RoleResources;
import com.adrian.repository.SysRoleRsRepository;
import com.adrian.service.RoleResourcesService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * @author asus
 * @ClassName RoleResourcesServiceTest
 * @description TODO
 * @Date 2018/11/7 14:32
 * @Version 1.0v
 **/
public class RoleResourcesServiceTest extends AuthServerApplicationTests {

    @Autowired
    private RoleResourcesService roleResourcesService;


    @Autowired
    private SysRoleRsRepository sysRoleRsRepository;


    @Test
    public void saveFatherTest() {
        RoleResources roleResources = new RoleResources();
        roleResources.setResourceType("Button");
        roleResources.setPath("/del");
        roleResources.setValue("");
        roleResources.setName("删除");
        roleResources.setIconCls("图标");
        roleResources.setEnabled(true);
        roleResources.setComponent("Home");
        SysRoleResources save = roleResourcesService.saveFather(roleResources);
        System.out.println(save.toString());
    }


    @Test
    public void addParentMountedChildResourcesTest() {
        boolean resources = roleResourcesService.addParentMountedChildResources(1L, "5,6");
        System.out.println(resources);
    }

    @Test
    public void findAllByComponentContainingTest() {
        Page<SysRoleResources> allByComponentContaining = roleResourcesService.findAllByComponentContaining(1, 20);
    }

    @Test
    public void findAllByComponentNotContainsTest() {
        Page<SysRoleResources> allByComponentNotContains = roleResourcesService.findAllByComponentNotContains(1, 20);
    }

    @Test
    public void findOne() {
        SysRoleResources one = roleResourcesService.findOne(1L);
    }

    @Test
    public void parentDelChildResourcesTest() {
        boolean resources = roleResourcesService.parentDelChildResources(1L, "5,6,13");
        System.out.println(resources);

    }



}
