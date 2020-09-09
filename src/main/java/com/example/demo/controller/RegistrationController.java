package com.example.demo.controller;

import com.example.demo.dto.IN.UserInPayload;
import com.example.demo.enums.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class RegistrationController {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private final UserRepo userRepo;

    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(path = "/reg")
    public String addUser(@RequestBody UserInPayload userDTO){
        if(userRepo.findByName(userDTO.getUsername())!=null) {
            logger.error("User already exists");
            return "User already exists";
        }

        User user=new User();
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getUsername());

        if(userDTO.isAdm())
            user.setRoles(Collections.singleton(Role.ADMIN));
        else user.setRoles(Collections.singleton(Role.USER));

        userRepo.save(user);
        logger.info("New user was saved");
        return "User was saved";
    }





















}
