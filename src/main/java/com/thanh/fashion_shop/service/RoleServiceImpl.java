package com.thanh.fashion_shop.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thanh.fashion_shop.domain.Permission;
import com.thanh.fashion_shop.domain.Role;
import com.thanh.fashion_shop.domain.respone.role.RoleCreatedRespone;
import com.thanh.fashion_shop.repository.PermissionRepository;
import com.thanh.fashion_shop.repository.RoleRepository;
import com.thanh.fashion_shop.util.exceptions.CommonException;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository repository, PermissionRepository permissionRepository) {
        this.repository = repository;
        this.permissionRepository = permissionRepository;
    }

    @Override
public RoleCreatedRespone createRole(Role reqRole) throws CommonException {
        if (this.repository.existsByName(reqRole.getName())) {
            throw new CommonException("Role already in system");
        }

        if (reqRole.getPermissions() != null) {
            List<Long> reqPermissions = reqRole.getPermissions().stream()
                    .map(item -> item.getId()).collect(Collectors.toList());
            List<Permission> permissions = this.permissionRepository.findByIdIn(reqPermissions);
            reqRole.setPermissions(permissions);
        }
        Role cuRole = this.repository.save(reqRole);
        RoleCreatedRespone res = new RoleCreatedRespone();
        res.setId(reqRole.getId());
        res.setName(reqRole.getName());
        res.setCreatedBy(reqRole.getCreatedBy());
        res.setCreatedDate(reqRole.getCreatedDate());
        res.setUpdatedAt(reqRole.getCreatedDate());
        res.setUpdatedBy(reqRole.getUpdatedBy());

        if (cuRole.getPermissions() != null) {
            List<String> permissionsStr = cuRole.getPermissions().stream()
                    .map(item -> item.getName()).collect(Collectors.toList());
            res.setPermissions(permissionsStr);
            ;
        }
        return res;
    }

    @Override
    public Role fetchRoleById(Long id) throws CommonException {
        Role role = this.repository.findById(id)
                .orElseThrow(() -> new CommonException("Role not found"));
        return role;
    }

}
