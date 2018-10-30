package com.github.jwtsecurityspringbootstarter.entity;

import io.jsonwebtoken.Claims;

public abstract class JwtUser {
    public JwtUser() {
    }

    public JwtUser(Claims claims) {

    }

    abstract JwtUser init(Claims claims);
}
