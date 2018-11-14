package com.adrian.controller;

import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * #@EnableGlobalMethodSecurity详解 3.1、@EnableGlobalMethodSecurity(securedEnabled=true)
 * 开启@Secured 注解过滤权限
 * <p>
 * 3.2、@EnableGlobalMethodSecurity(jsr250Enabled=true)
 * <p>
 * 开启@RolesAllowed 注解过滤权限
 * <p>
 * 3.3、@EnableGlobalMethodSecurity(prePostEnabled=true)
 * 使用表达式时间方法级别的安全性 4个注解可用
 * #@PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 * #@PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 * #@PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 * #@PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 *
 * @author fengdan
 * @date 2017/6/12
 */
@RequestMapping("/test")
@RestController
public class DemoController {

    @GetMapping("/cases")
    @PreAuthorize("hasAuthority('SysCfg')")
    public ResponseEntity<?> getDemo() {
        return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, "接口只能拥有系统管理权限的用户才能访问!"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DESIGNER')")
    @GetMapping("/designer")
    public ResponseEntity<?> getInfo() {
        return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, "接口只能拥有设计师角色的用户才能访问!"), HttpStatus.OK);
    }
}
