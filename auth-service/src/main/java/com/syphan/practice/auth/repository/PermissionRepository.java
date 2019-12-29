package com.syphan.practice.auth.repository;

import com.syphan.practice.auth.base.JpaQueryRepository;
import com.syphan.practice.auth.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaQueryRepository<Permission, Integer> {
    Permission findByCode(String code);
}
