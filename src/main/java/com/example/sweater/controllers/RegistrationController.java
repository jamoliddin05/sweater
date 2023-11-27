package com.example.sweater.controllers;

import com.example.sweater.models.Role;
import com.example.sweater.models.User;
import com.example.sweater.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepo;

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(User user, Model model) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if(userFromDB != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
