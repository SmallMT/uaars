package com.example.oauth.resource.server.resource_server.repository;

import com.example.oauth.resource.server.resource_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
    User findUserByLogin(String login);
}
