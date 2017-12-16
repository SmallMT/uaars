package com.example.oauth.resource.server.resource_server.configuartion;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Import(SecurityProblemSupport.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
{
	/* Empty code. Only for annotaions */

    private final SecurityProblemSupport problemSupport;

    public ResourceServerConfiguration( SecurityProblemSupport problemSupport) {

        this.problemSupport = problemSupport;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport);

        http.authorizeRequests().anyRequest().authenticated();
    }
}
