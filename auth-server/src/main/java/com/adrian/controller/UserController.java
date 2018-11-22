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
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 提供用户信息以供鉴权
 *
 * @author fengdan
 * @date 2018/10/12
 */

@Log4j
@RestController
@RequestMapping(value = "/authentication")
public class UserController {

    private final AdminService adminService;

    private final UserService userService;

    @Autowired
    public UserController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    /**
     * 用户信息校验
     *
     * @return 用户信息
     */
    @GetMapping(value = "/user")
    public Object user(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 获取登录用户的资源
     *
     * @return 用户的资源
     */
    @GetMapping(value = "/resource")
    public ResponseEntity<HttpStatusContent> resource() {
        UserDetailsImpl currentHr = UserDetailsUtils.getCurrentHr();
        LoginUserResources oneWithRolesByUsername = userService.findOneWithRolesByUsername(currentHr.getUsername());
        if (oneWithRolesByUsername.getSysRoleResources().size() > 0) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.SUCCESS, oneWithRolesByUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @api {POST} /authentication/user/register 注册用户
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription 用于注册用户
     * @apiParam {String} username 用户账户名
     * @apiParam {String} pwd 密码
     * @apiParamExample {json} 请求样例：
     * ?username=sodlinken&pwd=11223344
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {int} code 0 代表无错误 1代表有错误
     * @apiSuccessExample {json} 返回样例:
     * {
     * "code": 2000,
     * "message": "注册成功",
     * "obj": null
     * }
     */
    @PostMapping(value = "/user/register")
    public ResponseEntity<?> registeredUser(@Valid @RequestBody UserRegister register, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String defaultMessage = null;
            for (ObjectError error : bindingResult.getAllErrors()) {
                defaultMessage = error.getDefaultMessage();
            }
            log.info(bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL, defaultMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SysUser bySysUserName = userService.findByUserName(register.getUserName());
        if (bySysUserName != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL, register.getUserName() + ",用户已存在"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserRegister sysUser = adminService.createSysUser(register);
        if (sysUser != null) {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.REGISTRATION_SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
