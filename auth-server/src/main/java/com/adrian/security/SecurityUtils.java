package com.adrian.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author asus
 */
public final class SecurityUtils {
    public static String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String currentUserName = "admin";
        //if (!(authentication instanceof AnonymousAuthenticationToken)) {
        //    currentUserName = authentication.getName();
        //    return currentUserName;
        //} else {
            return "admin";
        //}
    }

}
