package com.thanh.fashion_shop.service;

import com.thanh.fashion_shop.domain.Role;
import com.thanh.fashion_shop.domain.respone.role.RoleCreatedRespone;
import com.thanh.fashion_shop.util.exceptions.CommonException;

public interface RoleService {
    RoleCreatedRespone createRole(Role role) throws CommonException;

    Role fetchRoleById(Long id) throws CommonException;
}
