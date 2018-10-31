package com.github.jwtsecurityspringbootstarter.config;

import com.github.jwtsecurityspringbootstarter.JwtTokenUtils;
import com.github.jwtsecurityspringbootstarter.entity.JwtUserFactory;
import com.github.jwtsecurityspringbootstarter.filter.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 手动配置方式
 * <pre>
 * http.apply(new JwtConfigurer(jwtTokenUtils, jwtUserFactory));
 *
 * @param <T>
 * @author a9043
 */
public class JwtConfigurer<T extends HttpSecurityBuilder<T>> extends AbstractHttpConfigurer<JwtConfigurer<T>, T> {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public JwtConfigurer(JwtTokenUtils jwtTokenUtils, JwtUserFactory jwtUserFactory) {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenUtils, jwtUserFactory);
    }

    @Override
    public void configure(T builder) throws Exception {
        builder
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
