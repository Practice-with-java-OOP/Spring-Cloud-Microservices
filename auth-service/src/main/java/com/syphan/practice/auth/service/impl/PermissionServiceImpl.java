package com.syphan.practice.auth.service.impl;

import com.syphan.common.api.enumclass.ErrType;
import com.syphan.common.api.exception.BIZException;
import com.syphan.common.dao.service.impl.BaseServiceImpl;
import com.syphan.practice.auth.dto.PermissionCreateDto;
import com.syphan.practice.auth.model.Permission;
import com.syphan.practice.auth.repository.PermissionRepository;
import com.syphan.practice.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Integer> implements PermissionService {

    private PermissionRepository repository;

    @Autowired
    protected PermissionServiceImpl(PermissionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Transactional
    @Override
    public Permission create(PermissionCreateDto data) throws BIZException {
        if (repository.findByCode(data.getCode().trim()) != null) throw BIZException.buildBIZException(
                ErrType.CONFLICT, "code.already.existed", String.format("%s%s%s", "Code [ ", data.getCode(), " ] already existed.")
        );

        Permission permission = new Permission();
        permission.setName(data.getName());
        permission.setCode(data.getCode());
        return repository.save(permission);
    }
}
