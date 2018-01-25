package com.example.oauth.resource.server.resource_server.service;


import com.example.oauth.resource.server.resource_server.domain.User_;
import com.example.oauth.resource.server.resource_server.repository.UserRepository;
import com.example.oauth.resource.server.resource_server.service.dto.UserCriteria;
import com.example.oauth.resource.server.resource_server.service.dto.UserDTO;
import com.example.oauth.resource.server.resource_server.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import com.example.oauth.resource.server.resource_server.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User>  {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<User> specification = createSpecification(criteria);
        final Page<User> result = userRepository.findAll(specification, page);
        return result.map(userMapper::userToUserDTO);
    }

    /**
     * Function to convert CarCriteria to a {@link Specifications}
     */
    private Specifications<User> createSpecification(UserCriteria criteria) {
        Specifications<User> specification = Specifications.where(null);
        if (criteria != null) {

            if (criteria.getIdentity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentity(), User_.identity));
            }
            if (criteria.getVerified()!= null) {
                specification = specification.and(buildSpecification(criteria.getVerified(),User_.verified));
            }
        }
        return specification;
    }


}
