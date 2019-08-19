package com.controllers;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;
    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();

        if(filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        }else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("filter", filter);
        model.addAttribute("messages",messages);

        return "main";
    }
    @PostMapping("add")
    public  String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam("file") MultipartFile file,
            @RequestParam String tag, Map<String,Object> model)
            throws IOException {
        Message message = new Message(text, tag, user);
        if (file != null && !file.getOriginalFilename().isEmpty()){
            File uplDir = new File(uploadPath);
            if (!uplDir.exists()){
                uplDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }

        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages",messages);

        return "main";
    }
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String,Object> model){
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
            model.put("messages", messages);
        }else {
            messages = messageRepository.findAll();
            model.put("messages", messages);
        }
        return "main";
    }
}
