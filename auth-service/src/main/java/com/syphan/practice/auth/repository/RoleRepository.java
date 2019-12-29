package com.syphan.practice.auth.repository;

import com.syphan.practice.auth.base.JpaQueryRepository;
import com.syphan.practice.auth.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaQueryRepository<Role, Integer> {
    Role findByCode(String code);
}
