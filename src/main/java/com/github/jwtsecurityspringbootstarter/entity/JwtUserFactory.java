package com.github.jwtsecurityspringbootstarter.entity;

import io.jsonwebtoken.Claims;

/**
 * Jwt生成工厂
 *
 * @author a9043
 */
public interface JwtUserFactory {
    JwtUser init(Claims claims);
}
