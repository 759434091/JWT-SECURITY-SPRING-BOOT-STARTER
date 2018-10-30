package com.github.jwtsecurityspringbootstarter.config;

import com.github.jwtsecurityspringbootstarter.entity.JwtUser;
import io.jsonwebtoken.Claims;

public interface JwtUserFactory {
    JwtUser init(Claims claims);
}
