package com.example.sweater.controllers;

import com.example.sweater.models.User;
import com.example.sweater.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(User user, Model model) {

        if(!userService.addUser(user)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
            model.addAttribute("message", "Пользователь подтверждён!");
        } else {
            model.addAttribute("message", "Активационный код не найден");
        }

        return "login";
    }

}
