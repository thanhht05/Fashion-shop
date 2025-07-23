package com.thanh.fashion_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanh.fashion_shop.domain.Permission;
import com.thanh.fashion_shop.service.PermissionService;
import com.thanh.fashion_shop.util.exceptions.CommonException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> handleCreatePermission(@RequestBody Permission reqPermission)
            throws CommonException {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.createPermission(reqPermission));
    }

}
