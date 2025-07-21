package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.domain.respone.PaginationResponse;
import com.thanh.fashion_shop.domain.respone.user.UserResponeDto;
import com.thanh.fashion_shop.service.UserService;
import com.thanh.fashion_shop.util.exceptions.CommonException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getMethodName() {
        return "Hello, it's me! i will kill you hahah whet the heel";
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponeDto> handleCreateUser(@Valid @RequestBody User reqUser) throws CommonException {
        String hashPassword = passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(hashPassword);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(reqUser));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponeDto> handleUpdateUser(@RequestBody User reqUser) throws CommonException {

        return ResponseEntity.ok().body(this.userService.updateUser(reqUser));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponeDto> handleGetUserById(@PathVariable("id") Long id) throws CommonException {
        return ResponseEntity.ok().body(this.userService.fetchUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<PaginationResponse> handleGetAllUser(@Filter Specification<User> spec, Pageable pageable) {
        return ResponseEntity.ok().body(this.userService.fetchAllUser(pageable, spec));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> handleDeleteUser(@PathVariable("id") Long id) throws CommonException {
        this.userService.deleteUserById(id);
        return ResponseEntity.ok().body(null);
    }

}
