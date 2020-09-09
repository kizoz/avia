
package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    .addFilterAfter(new JwtVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/",  "/reg").permitAll()
                    .antMatchers("/a/**").hasAuthority("ADMIN")
                    .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                    .anyRequest().authenticated();
    }
}

