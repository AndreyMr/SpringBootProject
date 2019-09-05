package com.controllers;

import com.entity.User;
import com.entity.dto.CaptchaResponseDto;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginController {
    //URL для проверки капчи
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @Value("${recaptcha.secret}")
    private String secret;

    @PostMapping ("/registration")
    public String addUser(
            @RequestParam ("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model){
        //строка для отправки url с заполнеными параметрами ?secret=%s&response=%s
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if(!captchaResponseDto.isSuccess()){
            model.addAttribute("captchaError","Fill cartcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if(isConfirmEmpty){
            model.addAttribute("password2Error", "Password confirmation can not be empty");
        }
        //если пароли не соввпадают то добавляем атрибут passwordError
        if(user.getPassword()!=null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");
        }
        // Если есть ошибки валидации то возвращаемся на страницу регистрации
        if(isConfirmEmpty || bindingResult.hasErrors() || !captchaResponseDto.isSuccess()){
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
