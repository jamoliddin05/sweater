package com.example.sweater.controllers;

import com.example.sweater.models.Role;
import com.example.sweater.models.User;
import com.example.sweater.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String saveEditedUser(
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form,
            @RequestParam String username
    ) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/user";
    }

    @PostMapping("subscribe/{user}")
    public String subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser) {
        userService.subscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }

    @PostMapping("unsubscribe/{user}")
    public String unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser) {

        userService.unsubscribe(currentUser, user);

        return "redirect:/user-messages/" + user.getId();
    }
}
