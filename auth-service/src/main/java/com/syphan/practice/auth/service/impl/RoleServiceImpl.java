package com.syphan.practice.auth.service.impl;

import com.syphan.common.api.enumclass.ErrType;
import com.syphan.common.api.exception.BIZException;
import com.syphan.common.dao.service.impl.BaseServiceImpl;
import com.syphan.practice.auth.dto.RoleCreateDto;
import com.syphan.practice.auth.model.Permission;
import com.syphan.practice.auth.model.Role;
import com.syphan.practice.auth.repository.PermissionRepository;
import com.syphan.practice.auth.repository.RoleRepository;
import com.syphan.practice.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {

    private RoleRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    protected RoleServiceImpl(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Transactional
    @Override
    public Role create(RoleCreateDto data) throws BIZException {
        if (repository.findByCode(data.getCode().trim()) != null) throw BIZException.buildBIZException(
                ErrType.CONFLICT, "code.already.existed", String.format("%s%s%s", "Code [ ", data.getCode(), " ] already existed.")
        );

        Set<Permission> permissions = null;

        if (!data.getPermissionIds().isEmpty()) {
            permissions = new HashSet<>(permissionRepository.findAllById(data.getPermissionIds()));
            if (permissions.size() != new HashSet<>(data.getPermissionIds()).size()) {
                throw BIZException.buildBIZException(ErrType.NOT_FOUND,
                        "permission.do.not.existed", String.format("%s%s%s", "Permission with id in list Id[ ", data.getPermissionIds(), "] do not existed"));
            }
        }

        Role role = new Role();
        role.setName(data.getName());
        role.setCode(data.getCode());
        role.setPermissions(permissions);
        return repository.save(role);
    }

}
