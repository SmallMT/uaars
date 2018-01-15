package com.example.oauth.resource.server.resource_server.repository;

import com.example.oauth.resource.server.resource_server.domain.BindEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BindEnterpriseRepository extends JpaRepository<BindEnterprise,Integer> {

    Optional<BindEnterprise> findOneById(Integer id);

    Optional<BindEnterprise> findOneByCreditCode(String creditCode);
}
