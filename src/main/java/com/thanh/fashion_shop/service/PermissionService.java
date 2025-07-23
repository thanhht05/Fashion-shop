package com.thanh.fashion_shop.service;

import com.thanh.fashion_shop.domain.Permission;
import com.thanh.fashion_shop.util.exceptions.CommonException;

public interface PermissionService {
    Permission createPermission(Permission reqPermission) throws CommonException;
}
