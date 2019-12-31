package com.syphan.practice.auth.service;

import com.syphan.common.api.exception.BIZException;
import com.syphan.common.dao.service.BaseService;
import com.syphan.practice.auth.dto.RoleCreateDto;
import com.syphan.practice.auth.model.Role;

public interface RoleService extends BaseService<Role, Integer> {

    Role create(RoleCreateDto data) throws BIZException;
}
