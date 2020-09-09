package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtVerifier extends OncePerRequestFilter {

    public String jwtKey = "eyJhbGciOiJIUkb2DKl-cCBiLbMDovI75f8mzUxMdXRdWICJhaAFmL7bIqYrkFMMob3JpdGllcyIiJ9eyJzupbG1pbm6IkFETUlOIn0U-mjXwfEUZiOiJhZUsy7yDVIwCpxCYbfOLejnDKVkjQbj4-ZUfLRv";

    public Key getKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token= header.replace("Bearer ", "");

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);

            String username = jws.getBody().getSubject();

            Authentication authentication= new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Arrays.stream(jws.getBody().get("authorities").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }catch (JwtException e){
            throw new IllegalStateException(String.format("Token cannot be trusted %s", token));
        }
        filterChain.doFilter(request, response);
    }
}
