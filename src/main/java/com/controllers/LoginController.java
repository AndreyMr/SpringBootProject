package com.controllers;

import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping ("/registration")
    public String addUser(User user, Map<String, Object> model){
        // addUser возвращает булево значение и если пользователь уже существует в базе возвращает false
        if(!userService.addUser(user)){
            model.put("message", "User exist");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivate = userService.activateUser(code);

        if(isActivate) model.addAttribute("message", "Email Confirmed");
        else {model.addAttribute("message", "Activation code is not found");}
        return "login";
    }

}
