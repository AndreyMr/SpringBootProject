package com.controllers;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
        //если валидация прошла успешно, то надо удалить из модели message, иначе форма добавления останется открытой
        model.addAttribute("message", null);

        messageRepository.save(message);
    }

    Iterable<Message> messages = messageRepository.findAll();
    model.addAttribute("messages", messages);

    return "main";
}

}
