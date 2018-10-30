package com.github.jwtsecurityspringbootstarter;

import com.github.jwtsecurityspringbootstarter.config.JwtUserFactory;
import com.github.jwtsecurityspringbootstarter.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@Import(AuthenticationConfiguration.class)
public class JwtConfigurerAdapter extends WebSecurityConfigurerAdapter {
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
