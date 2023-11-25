package com.example.sweater.controllers;

import com.example.sweater.models.Role;
import com.example.sweater.models.User;
import com.example.sweater.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepo;

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(User user, Map<String, Object> model) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if(userFromDB != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
