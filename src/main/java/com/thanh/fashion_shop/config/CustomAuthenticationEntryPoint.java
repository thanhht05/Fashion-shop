package com.thanh.fashion_shop.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanh.fashion_shop.domain.respone.RestRespone;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper; // convert object to json
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint(); // check error Bearer
                                                                                                 // token

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");
        RestRespone<Object> res = new RestRespone<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());

        String errorMessage = Optional.ofNullable(authException.getCause())
                .map(Throwable::getMessage)
                .orElse(authException.getMessage());
        res.setError(errorMessage);
        res.setMessage("Token không hợp lệ(hết hạn , không đúng định dạnh)");
        mapper.writeValue(response.getWriter(), res);
    }

}
