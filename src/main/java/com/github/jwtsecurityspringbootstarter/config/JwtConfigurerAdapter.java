package com.github.jwtsecurityspringbootstarter.config;

import com.github.jwtsecurityspringbootstarter.JwtTokenUtils;
import com.github.jwtsecurityspringbootstarter.entity.JwtUserFactory;
import com.github.jwtsecurityspringbootstarter.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 自动配置
 * 若不存在WebSecurityConfigurerAdapter Bean则自动配置该文件
 *
 * @author a9043
 */
@Configuration
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
public class JwtConfigurerAdapter extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean({JwtUserFactory.class, JwtTokenUtils.class})
    JwtAuthenticationFilter jwtAuthenticationFilter(@Autowired JwtTokenUtils jwtTokenUtils,
                                                    @Autowired JwtUserFactory jwtUserFactory) {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenUtils, jwtUserFactory);
        return jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
