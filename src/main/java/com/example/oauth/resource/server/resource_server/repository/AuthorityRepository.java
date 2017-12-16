package com.example.oauth.resource.server.resource_server.repository;

import com.example.oauth.resource.server.resource_server.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
