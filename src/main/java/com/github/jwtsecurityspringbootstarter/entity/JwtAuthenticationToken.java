package com.github.jwtsecurityspringbootstarter.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken<T> extends AbstractAuthenticationToken {
    private T t;

    public JwtAuthenticationToken(T t,
                                  Collection<? extends GrantedAuthority> authorities) {
        this(authorities);
        this.t = t;
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for
     *                    the principal
     *                    represented by this authentication object.
     */
    private JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return t;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public T getUser() {
        return t;
    }
}
