package com.controllers;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import com.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    //получаем страницу с сообщениями одного пользователя
    @GetMapping("/user-messages/{user}")
    public String userMessges(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            //если в модели нету атрибута message то required выставляем false. чтобы message был не обязаелен
            @RequestParam(required = false) Message message
    ){
        Set<Message> messages = user.getMessages();
        model.addAttribute("messages",messages);
        model.addAttribute("message",message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }
    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("messageText") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file

    ) throws IOException {
        if(message.getAuthor().equals(currentUser)){
            if (!StringUtils.isEmpty(text)) message.setMessageText(text);
            if (!StringUtils.isEmpty(tag)) message.setTag(tag);
            messageService.saveFile(message,file);
            messageRepository.save(message);
        }
        return "redirect:/user-messages/" + user;
    }
}
