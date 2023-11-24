package com.example.sweater.controllers;

import com.example.sweater.models.Message;
import com.example.sweater.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepo;

    @GetMapping
    public String mainPage(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String processMessage(@RequestParam String text, @RequestParam String tag,
                                 Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterSearch(@RequestParam String tag,
                               Map<String, Object> model) {
        Iterable<Message> messages;

        if(tag != null) {
            messages = messageRepo.findByTag(tag);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
}
