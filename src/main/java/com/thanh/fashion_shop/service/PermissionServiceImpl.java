package com.thanh.fashion_shop.service;

import org.springframework.stereotype.Service;

import com.thanh.fashion_shop.domain.Permission;
import com.thanh.fashion_shop.repository.PermissionRepository;
import com.thanh.fashion_shop.util.exceptions.CommonException;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(Permission reqPermission) throws CommonException {
        if (this.permissionRepository.existsByApiPathAndMethodAndModule(reqPermission.getApiPath(),
                reqPermission.getMethod(), reqPermission.getModule())) {
            throw new CommonException("Permission already in system");
        }
        return this.permissionRepository.save(reqPermission);
    }

}
