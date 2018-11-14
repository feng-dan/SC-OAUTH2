package com.adrian.common;

import com.adrian.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author asus
 * @ClassName UserDetailsUtils
 * @description TODO
 * @Date 2018/11/1 16:26
 * @Version 1.0v
 **/
public class UserDetailsUtils {
    public static UserDetailsImpl getCurrentHr() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}