package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.fashion_shop.domain.Role;
import com.thanh.fashion_shop.domain.respone.role.RoleCreatedRespone;
import com.thanh.fashion_shop.service.RoleService;
import com.thanh.fashion_shop.util.exceptions.CommonException;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleCreatedRespone> handleCreateRole(@Valid @RequestBody Role reqRole)
            throws CommonException {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.createRole(reqRole));
    }

}
