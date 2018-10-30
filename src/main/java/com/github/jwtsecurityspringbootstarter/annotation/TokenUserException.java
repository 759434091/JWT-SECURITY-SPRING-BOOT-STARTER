package com.github.jwtsecurityspringbootstarter.annotation;

/**
 * @author a9043
 */
public class TokenUserException extends RuntimeException {
    public TokenUserException() {
    }

    public TokenUserException(String message) {
        super(message);
    }
}
