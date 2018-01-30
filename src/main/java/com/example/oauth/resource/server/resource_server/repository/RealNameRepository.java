package com.example.oauth.resource.server.resource_server.repository;

import com.example.oauth.resource.server.resource_server.domain.RealName;
import com.example.oauth.resource.server.resource_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RealNameRepository extends JpaRepository<RealName,Integer>,JpaSpecificationExecutor<RealName> {

    /*通过实名认证信息*/
    @Modifying
    @Query("update RealName set state='通过' where id=:id")
    void pass(@Param("id") Integer id);

    /*不通过实名认证信息*/

    @Modifying
    @Query("update RealName set state='不通过' where id=:id")
    void notPass(@Param("id") Integer id);


    RealName findByLogin(String login);

}
