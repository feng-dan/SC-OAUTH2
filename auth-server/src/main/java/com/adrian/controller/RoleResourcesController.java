package com.adrian.controller;

import com.adrian.domain.SysRoleResources;
import com.adrian.dto.RoleResources;
import com.adrian.service.RoleResourcesService;
import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author asus
 * @ClassName ResourcesController
 * @description TODO
 * @Date 2018/11/5 15:56
 * @Version 1.0v
 **/
@Log4j
@RestController
@RequestMapping(value = "/resourced")
public class RoleResourcesController {

    private final RoleResourcesService roleResourcesService;

    @Autowired
    public RoleResourcesController(RoleResourcesService roleResourcesService) {
        this.roleResourcesService = roleResourcesService;
    }

    /**
     * 获取所有父级资源/已测试
     *
     * @return List<RoleInfo>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentContaining = roleResourcesService.findAllByComponentContaining(page - 1, size);
        if (allByComponentContaining.getContent().size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, allByComponentContaining), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取所有子级资源/已测试
     *
     * @param page
     * @param size
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/childes")
    public ResponseEntity<?> findNotAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentNotContains = roleResourcesService.findAllByComponentNotContains(page - 1, size);
        if (allByComponentNotContains.getContent().size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, allByComponentNotContains), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增父级资源 /已测试
     *
     * @param roleResources 角色资源
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/parent")
    public ResponseEntity<?> addParentResource(@RequestBody RoleResources roleResources) {
        SysRoleResources sysRoleResources = roleResourcesService.addParentResource(roleResources);
        if (sysRoleResources != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, sysRoleResources), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 新增子级资源 /已测试
     *
     * @param roleResources 角色资源
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/childes")
    public ResponseEntity<?> addChildResources(@RequestBody RoleResources roleResources) {
        SysRoleResources sysRoleResources = roleResourcesService.addChildResources(roleResources);
        if (sysRoleResources != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, sysRoleResources), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增父级资源挂载子级资源 /已测试
     *
     * @param parentId 父级资源
     * @param childIds 子级资源
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addParentMountedChildResources(Long parentId, String childIds) {

        childIds = org.apache.commons.lang.StringUtils.removeEnd(childIds, ",");
        SysRoleResources one = roleResourcesService.findOne(parentId);
        for (SysRoleResources sysRoleResources : one.getChildren()) {
            String[] resourceIdArray = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(childIds, ",");
            for (String resourceId : resourceIdArray) {
                if (String.valueOf(sysRoleResources.getId()).equals(resourceId)) {
                    return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL, sysRoleResources.getName() + ",已添加，不能重复添加"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        boolean resources = roleResourcesService.addParentMountedChildResources(parentId, childIds);
        if (resources) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除父级下的子级资源
     *
     * @param parentId 父级资源
     * @param childIds 子级资源
     * @return resources
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<?> parentDelChildResources(@PathVariable(value = "id") Long parentId, @RequestParam String childIds) {
        boolean resources = roleResourcesService.parentDelChildResources(parentId, childIds);
        if (resources) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * parentId 查询单个父级资源 /已测试
     *
     * @param parentId 父级资源id
     * @return SysRoleResources
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id:\\d+}/finding")
    public ResponseEntity<?> findOne(@PathVariable(value = "id") Long parentId) {
        SysRoleResources one = roleResourcesService.findOne(parentId);
        if (one.getName() != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, one), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
