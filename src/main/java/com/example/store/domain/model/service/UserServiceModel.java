package com.example.store.domain.model.service;


import com.example.store.domain.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserServiceModel {
    private String username;
    private String email;
    private String password;
    private Set<Role> authorities;
}
