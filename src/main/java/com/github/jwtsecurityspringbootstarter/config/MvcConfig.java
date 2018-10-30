package com.github.jwtsecurityspringbootstarter.config;

import com.github.jwtsecurityspringbootstarter.annotation.TokenUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(@Autowired AuthenticationManagerBuilder authenticationManagerBuilder) {
        return authenticationManagerBuilder.eraseCredentials(false);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenUserMethodArgumentResolver());
    }
}
