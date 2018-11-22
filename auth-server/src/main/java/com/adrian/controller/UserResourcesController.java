package com.adrian.controller;

import com.adrian.domain.SysUserResources;
import com.adrian.service.UserResourcesService;
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
 * 用户资源
 *
 * @author asus
 * @ClassName UserResourcesController
 * @description TODO
 * @Date 2018/11/19 9:39
 * @Version 1.0v
 **/
@Log4j
@RestController
@RequestMapping(value = "/upsource")
public class UserResourcesController {

    private final UserResourcesService userResourcesService;

    @Autowired
    public UserResourcesController(UserResourcesService userResourcesService) {
        this.userResourcesService = userResourcesService;
    }

    /**
     * 获取所有父级资源
     *
     * @return List<RoleInfo>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentContaining = userResourcesService.findAllByComponentContaining(page - 1, size);
        if (allByComponentContaining.getContent().size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, allByComponentContaining), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取所有子级资源
     *
     * @param page
     * @param size
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/childes")
    public ResponseEntity<?> findNotAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentNotContains = userResourcesService.findAllByComponentNotContains(page - 1, size);
        if (allByComponentNotContains.getContent().size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, allByComponentNotContains), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增父级资源
     *
     * @param userResources 用户资源
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/parent")
    public ResponseEntity<?> addParentResource(@RequestBody com.adrian.dto.UserResources userResources) {
        SysUserResources sysUserResources = userResourcesService.saveFather(userResources);
        if (sysUserResources != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 新增子级资源
     *
     * @param userResources 用户资源
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/childes")
    public ResponseEntity<?> addChildResources(@RequestBody com.adrian.dto.UserResources userResources) {
        SysUserResources sysUserResources = userResourcesService.saveChild(userResources);
        if (sysUserResources != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增父级资源挂载子级资源
     *
     * @param parentId 父级资源
     * @param childIds 子级资源
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addParentMountedChildResources(Long parentId, String childIds) {
        boolean resources = userResourcesService.addParentMountedChildResources(parentId, childIds);
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
    @DeleteMapping
    public ResponseEntity<?> parentDelChildResources(Long parentId, String childIds) {
        boolean resources = userResourcesService.parentDelChildResources(parentId, childIds);
        if (resources) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * parentId 查询单个父级资源
     *
     * @param parentId 父级资源id
     * @return SysRoleResources
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "finding")
    public ResponseEntity<?> findOne(Long parentId) {
        SysUserResources one = userResourcesService.findOne(parentId);
        if (one != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, one), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
