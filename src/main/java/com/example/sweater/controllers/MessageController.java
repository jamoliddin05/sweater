package com.example.sweater.controllers;

import com.example.sweater.models.Message;
import com.example.sweater.models.User;
import com.example.sweater.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepo;

    @GetMapping("/messages")
    public String mainPage(Model model,
                           @AuthenticationPrincipal User user,
                           @RequestParam(required = false) String tag) {
        Iterable<Message> messages = messageRepo.findAll();

        if(tag != null && !tag.isEmpty()) {
            messages = messageRepo.findByTag(tag);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", tag);
        model.addAttribute("user", user);

        return "message_view";
    }

    @PostMapping("/messages")
    public String processMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        Message message = new Message(text, tag, user);
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "redirect:/messages";
    }
}
