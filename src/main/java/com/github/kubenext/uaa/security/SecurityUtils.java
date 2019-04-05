package com.github.kubenext.uaa.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * 安全工具类
 * @author lishangjin
 */
public final class SecurityUtils {

    /**
     * 获取当前用户的登录信息
     * @return
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        return userDetails.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    /**
     * 检查用户是否已通过身份验证
     * @return
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication ->
                    authentication.getAuthorities().stream().noneMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
                .orElse(false);
    }

    /**
     * 检查当前用户是否拥有指定角色权限
     * @param authority
     * @return
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }


}
