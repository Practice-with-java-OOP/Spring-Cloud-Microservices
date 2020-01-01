package com.syphan.practice.auth.repository;

import com.syphan.common.dao.dao.JpaQueryRepository;
import com.syphan.practice.auth.model.AuthClientDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthClientRepository extends JpaQueryRepository<AuthClientDetails, Integer> {

    Optional<AuthClientDetails> findByClientId(String clientId);
}
