package com.example.oauth.resource.server.resource_server.service;

import com.example.oauth.resource.server.resource_server.domain.*;
import com.example.oauth.resource.server.resource_server.repository.BindEnterpriseRepository;
import com.example.oauth.resource.server.resource_server.repository.RealNameRepository;
import com.example.oauth.resource.server.resource_server.repository.UserRepository;
import com.example.oauth.resource.server.resource_server.service.dto.BindEnterpriseCriteria;
import com.example.oauth.resource.server.resource_server.service.dto.UserCriteria;
import com.example.oauth.resource.server.resource_server.service.filter.Filter;
import com.example.oauth.resource.server.resource_server.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BindEnterpriseQueryService extends QueryService<BindEnterprise>  {

    private final Logger log = LoggerFactory.getLogger(BindEnterpriseQueryService.class);


    private final BindEnterpriseRepository bindEnterpriseRepository;
    private final UserRepository userRepository;

    public BindEnterpriseQueryService(BindEnterpriseRepository bindEnterpriseRepository,UserRepository userRepository) {
        this.bindEnterpriseRepository = bindEnterpriseRepository;
        this.userRepository= userRepository;
    }

    @Transactional(readOnly = true)
    public Page<BindEnterprise> findByCriteria(BindEnterpriseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<BindEnterprise> specification = createSpecification(criteria);
        final Page<BindEnterprise> result = bindEnterpriseRepository.findAll(specification, page);
        return result;
    }

    /**
     * Function to convert CarCriteria to a {@link Specifications}
     */
    private Specifications<BindEnterprise> createSpecification(BindEnterpriseCriteria criteria) {
        Specifications<BindEnterprise> specification = Specifications.where(null);
        if (criteria != null) {

            if (criteria.getCreditCode()!= null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditCode(), BindEnterprise_.creditCode));
            }
            if (criteria.getLogin()!=null){
                    specification = specification.and(buildReferringEntitySpecification(criteria.getLogin(),BindEnterprise_.user,User_.login));

            }
            if (criteria.getState()!=null){
                specification=specification.and(buildStringSpecification(criteria.getState(),BindEnterprise_.state));
            }
        }
        return specification;
    }

}
