package com.example.demo.dto.IN;

import lombok.Data;

@Data
public class UserInPayload {

    private String username;

    private String password;

    private boolean adm =false;
}
