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
 * 角色控制器
 *
 * @author fengdan
 */
@Log4j
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;

    private final SysRoleRepository sysRoleRepository;

    @Autowired
    public RoleController(RoleService roleService, SysRoleRepository sysRoleRepository) {
        this.roleService = roleService;
        this.sysRoleRepository = sysRoleRepository;
    }

    /**
     * 创建角色/已测试完成
     *
     * @param roleInfo 角色信息
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleInfo roleInfo) {
        SysRole sysSysRoleByName = sysRoleRepository.findSysRoleByName(roleInfo.getName());
        //判断角色是否存在
        if (sysSysRoleByName.getName() != null && !"".equals(sysSysRoleByName.getName())) {
            log.info(roleInfo.getName() + ",角色已存在");
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL, roleInfo.getName() + ",角色已存在"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RoleInfo info = roleService.create(roleInfo);
        log.info("添加的角色是:" + info.getName());
        if (info.getName() != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, info), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改角色信息/已测试完成
     *
     * @param roleInfo
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<?> update(@RequestBody RoleInfo roleInfo, @PathVariable(value = "id", required = false) String rid) {
        RoleInfo update = roleService.update(roleInfo);
        if (update != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, update), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除角色/已测试完成
     *
     * @param id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean delete = roleService.delete(id);
        if (delete) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取角色详情/已测试完成 可以修改 角色详情包括角色信息以及角色资源 \已修改
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getInfo(@PathVariable Long id) {
        RoleInfo info = roleService.getInfo(id);
        if (info != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, info), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取所有角色/已测试完成
     *
     * @return List<RoleInfo>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleInfo> roleInfos = roleService.findAll();
        if (roleInfos != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, roleInfos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取角色的所有资源/已测试完成
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id:\\d+}/resource")
    public ResponseEntity<?> getRoleResources(@PathVariable Long id) {
        List<SysRoleResources> sysRoleResources = roleService.getRoleResources(id);
        if (sysRoleResources.size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, sysRoleResources), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 创建角色的资源/已测试完成
     *
     * @param rid
     * @param ids
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id:\\d+}/resource")
    public ResponseEntity<?> createRoleResource(@PathVariable(value = "id") Long rid, String ids) {
        boolean resource = roleService.setRoleResources(rid, ids);
        if (resource) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
