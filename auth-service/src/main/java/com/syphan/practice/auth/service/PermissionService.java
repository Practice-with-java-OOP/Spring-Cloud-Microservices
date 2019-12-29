package com.syphan.practice.auth.service;

import com.syphan.practice.auth.base.BaseService;
import com.syphan.practice.auth.dto.PermissionCreateDto;
import com.syphan.practice.auth.model.Permission;

public interface PermissionService extends BaseService<Permission, Integer> {

    Permission create(PermissionCreateDto data);
}