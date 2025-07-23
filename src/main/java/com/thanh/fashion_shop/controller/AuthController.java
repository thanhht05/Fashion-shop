package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.domain.request.ReqLoginDto;
import com.thanh.fashion_shop.domain.respone.LoginResponeDto;
import com.thanh.fashion_shop.domain.respone.user.UserResponeDto;
import com.thanh.fashion_shop.service.UserService;
import com.thanh.fashion_shop.util.SecurityUtil;
import com.thanh.fashion_shop.util.annotations.ApiMessage;
import com.thanh.fashion_shop.util.exceptions.CommonException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authBuilder;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authBuilder, UserService userService,
            SecurityUtil securityUtil) {
        this.authBuilder = authBuilder;
        this.userService = userService;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/auth/login")
    @ApiMessage("Login")
    public ResponseEntity<LoginResponeDto> handleLogin(@RequestBody ReqLoginDto reqLogin) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                reqLogin.getUsername(), reqLogin.getPassword());

        Authentication authentication = authBuilder.getObject()
                .authenticate(authenticationToken);
        User user = this.userService.fetchUserByUsername(reqLogin.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponeDto res = new LoginResponeDto();
        LoginResponeDto.UserLogin userLogin = new LoginResponeDto.UserLogin();
        userLogin.setId(user.getId());
        userLogin.setName(user.getName());
        userLogin.setEmail(user.getEmail());

        res.setUser(userLogin);
        String accessToken = this.securityUtil.createAccessToken(authentication.getName(), res);
        res.setAccessToken(accessToken);
        String refreshToken = this.securityUtil.createRefreshToken(accessToken, res);
        this.userService.updateUserToken(authentication.getName(), refreshToken);

        // set cookie
        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(res);
    }

    @GetMapping("/auth/account")
    public ResponseEntity<LoginResponeDto.UserGetAccount> handleGetAccount() {
        String email = SecurityUtil.getCurrentUserLogin().orElse("");
        User userDb = this.userService.fetchUserByUsername(email);

        LoginResponeDto.UserGetAccount userGetAccount = new LoginResponeDto.UserGetAccount();

        LoginResponeDto.UserLogin userLogin = new LoginResponeDto.UserLogin(userDb.getId(), userDb.getEmail(),
                userDb.getName());

        userGetAccount.setUser(userLogin);
        return ResponseEntity.ok().body(userGetAccount);
    }

    @PostMapping("/auth/register")
    @ApiMessage("Register a new user")
    public ResponseEntity<UserResponeDto> handleRegister(@Valid @RequestBody User reqUser) throws CommonException {
        UserResponeDto user = this.userService.createUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
