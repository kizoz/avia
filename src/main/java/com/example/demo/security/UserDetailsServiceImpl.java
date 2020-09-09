package com.example.demo.security;

import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(Objects.nonNull(userRepo.findByName(username)))
        return userRepo.findByName(username);
        else throw new UsernameNotFoundException(username);
    }
}
