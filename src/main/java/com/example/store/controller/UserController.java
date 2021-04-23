package com.example.store.controller;

import com.example.store.domain.model.binding.UserRegisterBindingModel;
import com.example.store.domain.model.service.UserServiceModel;
import com.example.store.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String getLoginForm() {
        return "/user/sign-in";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterBindingModel());
        return "/user/sign-up";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute UserRegisterBindingModel userModel, BindingResult result) {
        this.userService.existsByUsername(userModel.getUsername());
        if (result.hasErrors()) {
            return "redirect:/register";
        }
        UserServiceModel serviceModel = this.modelMapper.map(userModel, UserServiceModel.class);
        this.userService.save(serviceModel);
        return "redirect:login";
    }

}
