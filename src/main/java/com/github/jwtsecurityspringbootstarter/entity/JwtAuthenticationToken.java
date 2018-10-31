package com.github.jwtsecurityspringbootstarter.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private JwtUser jwtUser;

    public JwtAuthenticationToken(JwtUser jwtUser) {
        this(jwtUser.getGrantedAuthorities());
        this.jwtUser = jwtUser;
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
        return jwtUser;
    }

    @Override
    public Object getPrincipal() {
        return jwtUser.getPrincipal();
    }

    public JwtUser getUser() {
        return jwtUser;
    }
}
