package com.controllers;

import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
    public String addUser(
            @RequestParam ("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model){
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if(isConfirmEmpty){
            model.addAttribute("password2Error", "Password confirmation can not be empty");
        }
        //если пароли не соввпадают то добавляем атрибут passwordError
        if(user.getPassword()!=null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");
        }
        // Если есть ошибки валидации то возвращаемся на страницу регистрации
        if(isConfirmEmpty || bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        // addUser возвращает булево значение и если пользователь уже существует в базе возвращает false
        if(!userService.addUser(user)){
            model.addAttribute("messageType","danger");
            model.addAttribute("message", "User exist");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivate = userService.activateUser(code);

        if(isActivate) {
            model.addAttribute("messageType","success");
            model.addAttribute("message", "Email Confirmed");
        }
        else {
            model.addAttribute("messageType","danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }

}
