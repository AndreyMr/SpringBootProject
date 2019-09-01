package com.controllers;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }
    //мэппинг по идентификатору user из БД /user/{user.id}
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String,String> form,
            @RequestParam("userId") User user
    ){
        userService.saveUser(username, user,form);
        return "redirect:/user";
    }


    //отображаем текущий профиль пользователя
    @GetMapping("profile")
    public String getProfile(Model model,
                             //берем пользователя из контекста Spring Security
                             @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }


    @PostMapping("profile")
        public String updateProfile(
                //берем пользователя из контекста Spring Security
                @AuthenticationPrincipal User user,
                //ожидаем из формы password
                @RequestParam String password,
                //ожидаем из формы email
                @RequestParam String email
    ){
        //обновляем пользователя в БД
        userService.updateUser(user, password, email);
        //перенаправляем обратно на просмотр профайлв пользователя
        return "redirect:/user/profile";
    }



}
