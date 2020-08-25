/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author wagne
 */
@Component
public class JwtTokenService {

    private String secret;
    private Long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String username) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpDate(createdDate);
        System.out.println(expirationDate);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
