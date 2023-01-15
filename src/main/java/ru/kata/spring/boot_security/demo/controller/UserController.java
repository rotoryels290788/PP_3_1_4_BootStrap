package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
    @GetMapping(value = "/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("messages", userServiceImp.getEmail(principal.getName()));
        return "user";
    }


}




