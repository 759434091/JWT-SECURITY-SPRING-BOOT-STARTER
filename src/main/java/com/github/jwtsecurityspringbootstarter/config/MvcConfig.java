package com.github.jwtsecurityspringbootstarter.config;

import com.github.jwtsecurityspringbootstarter.annotation.TokenUserMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC参数注入
 *
 * @author a9043
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenUserMethodArgumentResolver());
    }
}
