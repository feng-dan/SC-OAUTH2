/**
 *
 */
package com.adrian.controller;

import com.adrian.domain.SysRole;
import com.adrian.domain.SysRoleResources;
import com.adrian.dto.RoleInfo;
import com.adrian.repository.SysRoleRepository;
import com.adrian.service.RoleService;
import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fengdan
 */
@Log4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    /**
     * 创建角色
     *
     * @param roleInfo 角色信息
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleInfo roleInfo) {
        SysRole sysRoleByName = sysRoleRepository.findSysRoleByName(roleInfo.getName());
        //判断角色是否存在
        if (sysRoleByName != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL, roleInfo.getName() + ",角色已存在"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RoleInfo info = roleService.create(roleInfo);
        log.info("添加的角色是:" + info.getName());
        if (info.getId() != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, info), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 修改角色信息
     *
     * @param roleInfo
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody RoleInfo roleInfo) {
        RoleInfo update = roleService.update(roleInfo);
        if (update != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, update), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean delete = roleService.delete(id);
        if (delete) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取角色详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable Long id) {
        RoleInfo info = roleService.getInfo(id);
        if (info != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, info), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取所有角色
     *
     * @return List<RoleInfo>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleInfo> roleInfos = roleService.findAll();
        if (roleInfos != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, roleInfos), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取角色的所有资源
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/resource")
    public ResponseEntity<?> getRoleResources(@PathVariable Long id) {
        List<SysRoleResources> roleResources = roleService.getRoleResources(id);
        if (roleResources != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, roleResources), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 创建角色的资源
     *
     * @param id
     * @param ids
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/resource")
    public ResponseEntity<?> createRoleResource(@PathVariable Long id, String ids) {
        boolean resource = roleService.setRoleResources(id, ids);
        if (resource) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
