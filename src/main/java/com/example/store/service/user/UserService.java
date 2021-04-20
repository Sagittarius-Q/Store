package com.example.store.service.user;

import com.example.store.domain.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserServiceModel userServiceModel);
    void existsByUsername(String username);
}
