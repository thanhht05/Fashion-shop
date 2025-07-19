package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
    @GetMapping("/")
    public String getMethodName() {
        return "Hello, it's me! i will kill you hahah";
    }

}
