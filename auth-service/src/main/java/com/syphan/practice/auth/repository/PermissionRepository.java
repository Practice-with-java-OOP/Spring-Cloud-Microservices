package com.syphan.practice.auth.repository;

import com.syphan.common.dao.dao.JpaQueryRepository;
import com.syphan.practice.auth.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaQueryRepository<Permission, Integer> {
    Permission findByCode(String code);
}
