package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public String jwtKey = "eyJhbGciOiJIUkb2DKl-cCBiLbMDovI75f8mzUxMdXRdWICJhaAFmL7bIqYrkFMMob3JpdGllcyIiJ9eyJzupbG1pbm6IkFETUlOIn0U-mjXwfEUZiOiJhZUsy7yDVIwCpxCYbfOLejnDKVkjQbj4-ZUfLRv";

    public Key getKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest authRequest=new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            ));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String token= Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .signWith(getKey())
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}