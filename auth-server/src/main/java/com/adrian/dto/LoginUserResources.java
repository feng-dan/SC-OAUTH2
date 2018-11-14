package com.adrian.dto;

import com.adrian.domain.SysRoleResources;
import com.adrian.domain.SysUserResources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author asus
 * @ClassName LoginUserResources
 * @description TODO
 * @Date 2018/11/1 16:19
 * @Version 1.0v
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResources implements Serializable {
    private static final long serialVersionUID = -675090157946011822L;
    private Set<SysRoleResources> roleResources = new HashSet<>();
    private Set<SysUserResources> userResources = new HashSet<>();
}
