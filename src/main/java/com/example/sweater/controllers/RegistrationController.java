package com.example.sweater.controllers;

import com.example.sweater.models.User;
import com.example.sweater.models.dto.CaptchaResponseDTO;
import com.example.sweater.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@RequestParam("password2") String passwordConfirmation,
                           @RequestParam("g-recaptcha-response") String captchaResponse,
                           @Valid User user,
                           BindingResult bindingResult,
                           Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(),
                CaptchaResponseDTO.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Заполните Captcha");
        }

        boolean isConfirmationEmpty = StringUtils.isEmpty(passwordConfirmation);

        if (isConfirmationEmpty) {
            model.addAttribute("passwordError2", "Введите пароль снова");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Пароли разные!");
            return "registration";
        }

        if (isConfirmationEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь подтверждён!");
        } else {
            model.addAttribute("messageType", "dangerse ");
            model.addAttribute("message", "Активационный код не найден");
        }

        return "login";
    }

}
