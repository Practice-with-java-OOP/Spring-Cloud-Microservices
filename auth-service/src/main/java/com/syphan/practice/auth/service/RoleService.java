package com.syphan.practice.auth.service;

import com.syphan.practice.auth.base.BaseService;
import com.syphan.practice.auth.dto.RoleCreateDto;
import com.syphan.practice.auth.model.Role;

public interface RoleService extends BaseService<Role, Integer> {

    Role create(RoleCreateDto data);
}
