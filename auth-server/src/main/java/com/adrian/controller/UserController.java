package com.adrian.controller;

import com.adrian.common.UserDetailsUtils;
import com.adrian.domain.SysUser;
import com.adrian.dto.LoginUserResources;
import com.adrian.dto.UserRegister;
import com.adrian.security.UserDetailsImpl;
import com.adrian.service.AdminService;
import com.adrian.service.UserService;
import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供用户信息以供鉴权
 * Principal user
 *
 * @author fengdan
 * @date 2018/10/12
 */
@Log4j
@RestController
@Api("用户相关的api接口")
@RequestMapping("/authentication")
public class UserController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    /**
     * 用户信息校验
     *
     * @return 用户信息
     */
    @ApiOperation(value = "用户信息校验")
    @GetMapping("/user")
    public Object user(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 获取登录用户的资源
     *
     * @return 用户的资源
     */
    @ApiOperation(value = "获取登录用户的资源")
    @GetMapping("/resource")
    public ResponseEntity<HttpStatusContent> resource() {
        UserDetailsImpl currentHr = UserDetailsUtils.getCurrentHr();
        LoginUserResources oneWithRolesByUsername = userService.findOneWithRolesByUsername(currentHr.getUsername());
        if (oneWithRolesByUsername.getRoleResources().size() > 0) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, oneWithRolesByUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiImplicitParam(name = "username", value = "用户名", paramType = "text", required = true, dataType = "String")
    @ApiOperation(value = "注册用户", notes = "注册用户")
    @PostMapping("/user/registered")
    public ResponseEntity<?> registeredUser(UserRegister register) {
        SysUser byUserName = userService.findByUserName(register.getUserName());
        if (byUserName != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL, register.getUserName() + ",用户已存在"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserRegister sysUser = adminService.createSysUser(register);
        if (sysUser != null) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
