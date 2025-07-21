package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.domain.request.ReqLoginDto;
import com.thanh.fashion_shop.domain.respone.LoginResponeDto;
import com.thanh.fashion_shop.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authBuilder;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authBuilder, UserService userService) {
        this.authBuilder = authBuilder;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponeDto> handleLogin(@RequestBody ReqLoginDto reqLogin) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                reqLogin.getUsername(), reqLogin.getPassword());

        Authentication authentication = authBuilder.getObject()
                .authenticate(authenticationToken);
        User user = this.userService.fetchUserByUsername(reqLogin.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponeDto res = new LoginResponeDto();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setUsername(user.getEmail());
        return ResponseEntity.ok().body(res);
    }

}
