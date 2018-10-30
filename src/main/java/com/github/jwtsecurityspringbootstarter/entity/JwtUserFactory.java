package com.github.jwtsecurityspringbootstarter.entity;

import io.jsonwebtoken.Claims;

public interface JwtUserFactory {
    JwtUser init(Claims claims);
}
