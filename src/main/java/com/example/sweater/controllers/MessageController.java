package com.example.sweater.controllers;

import com.example.sweater.models.Message;
import com.example.sweater.models.User;
import com.example.sweater.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/messages")
    public String mainPage(Model model, @RequestParam(required = false) String tag) {
        Iterable<Message> messages = messageRepo.findAll();

        if (tag != null && !tag.isEmpty()) {
            messages = messageRepo.findByTag(tag);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", tag);

        return "message_view";
    }

    @PostMapping("/messages")
    public String processMessage(@AuthenticationPrincipal User user,
                                 @Valid Message message,
                                 BindingResult bindingResult,
                                 Model model,
                                 @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            save(message, file);

            model.addAttribute("message", null);
            messageRepo.save(message);
        }


        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        if(bindingResult.hasErrors()) {
            return "message_view";
        }

        return "redirect:/messages";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {
        Set<Message> messages = user.getMessages();
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
            ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            save(message, file);
            messageRepo.save(message);
        }

        return "redirect:/user-messages/" + user;
    }

    private void save(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }
}
