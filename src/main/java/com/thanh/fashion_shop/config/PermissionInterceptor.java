package com.thanh.fashion_shop.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.thanh.fashion_shop.domain.Permission;
import com.thanh.fashion_shop.domain.Role;
import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.service.UserService;
import com.thanh.fashion_shop.util.SecurityUtil;
import com.thanh.fashion_shop.util.exceptions.CommonException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String reqUri = request.getRequestURI();
        String httpMethod = request.getMethod();

        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email != null) {
            User user = this.userService.fetchUserByUsername(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    boolean isAllow = role.getPermissions().stream()
                            .anyMatch(item -> item.getApiPath().equals(path) && item.getMethod().equals(httpMethod));
                    if (!isAllow) {
                        throw new CommonException("Ko co quyen. get out here");
                    }

                } else {
                    throw new CommonException("Cut!");
                }
            }
        }

        System.out.println(path);
        System.out.println(reqUri);
        System.out.println(httpMethod);
        return true;
    }

}
