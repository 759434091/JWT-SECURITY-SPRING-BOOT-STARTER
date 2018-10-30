package com.github.jwtsecurityspringbootstarter;

import com.github.jwtsecurityspringbootstarter.config.JwtUserFactory;
import com.github.jwtsecurityspringbootstarter.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@Import(AuthenticationConfiguration.class)
public class JwtConfigurer<T extends HttpSecurityBuilder<T>> extends AbstractHttpConfigurer<JwtConfigurer<T>, T> {
    @Resource
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public void init(T builder) throws Exception {
        authenticationManagerBuilder.eraseCredentials(false);
    }

    @Override
    public void configure(T builder) throws Exception {
        builder
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Configuration
    @ConditionalOnMissingBean(JwtUserFactory.class)
    static class JwtUserFactoryNotFound {
        public JwtUserFactoryNotFound() {
            throw new RuntimeException("JwtUserFactory Not Found");
        }
    }

    @Configuration
    @ConditionalOnMissingBean(JwtTokenUtils.class)
    static class JwtTokenUtilsNotFound {
        public JwtTokenUtilsNotFound() {
            throw new RuntimeException("JwtTokenUtils Not Found");
        }
    }
}
