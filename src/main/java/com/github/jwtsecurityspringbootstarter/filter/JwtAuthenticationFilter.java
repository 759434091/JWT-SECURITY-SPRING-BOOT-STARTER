package com.github.jwtsecurityspringbootstarter.filter;

import com.github.jwtsecurityspringbootstarter.JwtTokenUtils;
import com.github.jwtsecurityspringbootstarter.entity.JwtUserFactory;
import com.github.jwtsecurityspringbootstarter.entity.JwtAuthenticationToken;
import com.github.jwtsecurityspringbootstarter.entity.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author a9043
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenUtils jwtTokenUtils;
    private JwtUserFactory jwtUserFactory;

    private JwtAuthenticationFilter() {
    }

    public JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtils, JwtUserFactory jwtUserFactory) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.jwtUserFactory = jwtUserFactory;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        if (token.length() <= 0 ||
                SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims;
        JwtUser jwtUser;

        //解析token
        try {
            claims = jwtTokenUtils.getClaims(token);
            jwtUser = jwtUserFactory.init(claims);

        } catch (MalformedJwtException | SignatureException | ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        //设定Authentication
        JwtAuthenticationToken ywAuthenticationToken =
                new JwtAuthenticationToken(jwtUser);
        ywAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        ywAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(ywAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}
