package com.example.Restaurant.controller;

import com.example.Restaurant.repository.UserRepository;
import com.example.Restaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    String login(User user){
        return "/login";
    }

    @GetMapping("/home")
    public String openUserForm(User user){
        return "home";
    }
}

