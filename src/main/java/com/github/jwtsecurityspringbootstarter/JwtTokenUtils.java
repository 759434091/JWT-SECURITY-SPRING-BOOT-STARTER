package com.github.jwtsecurityspringbootstarter;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JwtTokenUtils {
    private SignatureAlgorithm signatureAlgorithm;
    private SecretKeySpec key;
    private String issuer;


    public JwtTokenUtils(String issuer, String stringKey) {
        this(issuer, SignatureAlgorithm.HS512, stringKey);
    }

    public JwtTokenUtils(String issuer, SignatureAlgorithm signatureAlgorithm, String stringKey) {
        byte[] encodedKey = Base64.getEncoder().encode(stringKey.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        this.issuer = issuer;
        this.signatureAlgorithm = signatureAlgorithm;
        this.key = secretKeySpec;
    }

    public JwtTokenUtils(String issuer, SecretKeySpec key) {
        this(issuer, SignatureAlgorithm.HS512, key);
    }

    public JwtTokenUtils(String issuer, SignatureAlgorithm signatureAlgorithm, SecretKeySpec key) {
        this.issuer = issuer;
        this.signatureAlgorithm = signatureAlgorithm;
        this.key = key;
    }

    public String createJWT(JwtBuilder jwtBuilder) {
        return jwtBuilder.compact();
    }

    /**
     * Create Token
     *
     * @param claims       声明
     * @param expireField  时间位
     * @param expireAmount 时间长
     * @return token
     */
    public String createJWT(Map<String, Object> claims, int expireField, int expireAmount) {
        Date now = Calendar.getInstance().getTime();
        Calendar expireCal = Calendar.getInstance();
        expireCal.add(expireField, expireAmount);
        Date expire = expireCal.getTime();

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", signatureAlgorithm.getValue())
                .setIssuedAt(now)
                .setClaims(claims)
                .setIssuer(issuer)
                .setExpiration(expire)
                .signWith(signatureAlgorithm, key);
        return builder.compact();
    }

    /**
     * Parse Claims
     *
     * @param JwtStr token
     * @return claims
     * @throws SignatureException  err
     * @throws ExpiredJwtException err
     */
    public Claims getClaims(String JwtStr) throws SignatureException, ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(JwtStr)
                .getBody();
        return Optional
                .ofNullable(claims)
                .filter(claim -> claim.getExpiration() == null || !new Date().after(claim.getExpiration()))
                .orElse(null);
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public SecretKeySpec getKey() {
        return key;
    }

    public void setKey(SecretKeySpec key) {
        this.key = key;
    }
}
