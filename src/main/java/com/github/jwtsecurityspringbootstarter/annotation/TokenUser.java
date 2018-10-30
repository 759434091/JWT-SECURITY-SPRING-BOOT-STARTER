package com.github.jwtsecurityspringbootstarter.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenUser {
    boolean required() default true;
}