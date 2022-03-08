package com.solution.developerManager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    @Autowired
    private Environment env;

    @Value("${jwt.expiration}")
    private String expirationHours;

    public String generateToken(Map<String, Object> claims) throws IllegalArgumentException {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateUtils.addHours(new Date(), Integer.parseInt(expirationHours)))
                .signWith(Keys.hmacShaKeyFor(env.getProperty("SECRET_KEY").getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Jws<Claims> validateTokenAndRetrieveSubject(String token) {
            return Jwts.parserBuilder()
                    .require("iss", "GP")
                    .setSigningKey(Keys.hmacShaKeyFor(env.getProperty("SECRET_KEY").getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
    }
}
