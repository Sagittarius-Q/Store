package com.example.store.domain.model.binding;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserRegisterBindingModel {
    @Size(min = 4, max = 10, message
            = "User name must be between 4 and 10 characters")
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 4, max = 10, message
            = "password must be between 4 and 10 characters")
    private String password;
}
