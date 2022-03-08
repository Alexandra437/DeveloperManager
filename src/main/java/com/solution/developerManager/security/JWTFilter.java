package com.solution.developerManager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class JWTFilter extends OncePerRequestFilter {


    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        try {
            if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);
                if (jwt != null && !jwt.isBlank()) {
                    createAuthentication(jwt).ifPresent(authentication -> {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }
            }
        } catch (IncorrectClaimException exception) {
            response.setStatus(403);
        }
        filterChain.doFilter(request, response);
    }


    public Optional<Authentication> createAuthentication(String token) {

        Jws<Claims> jwsClaims = jwtUtil.validateTokenAndRetrieveSubject(token);
        if (jwsClaims == null) {
            return Optional.empty();
        }

        Claims claims = jwsClaims.getBody();

        String scopesString = claims.get("roles").toString();
        String[] authStrings = scopesString.replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",");

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(authStrings)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String subject = claims.getSubject();
        org.springframework.security.core.userdetails.User principal = new User(subject, "", authorities);

        return Optional.of(new UsernamePasswordAuthenticationToken(principal, token, authorities));
    }


}
