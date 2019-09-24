package com.controllers;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import com.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;


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

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(user);
        //если bindingResult имеет ошибки
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            // помещаем в модель карту с ошибками
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
            // сохранение пользователя происходит только если ошибки отсутствуют
        } else {
            //сохраняем файл
            messageService.saveFile(message, file);
            //если валидация прошла успешно, то надо удалить из модели message, иначе форма добавления останется открытой
            model.addAttribute("message", null);

            messageRepository.save(message);
        }

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }
}
