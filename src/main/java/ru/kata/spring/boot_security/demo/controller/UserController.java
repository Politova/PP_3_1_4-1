package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.ui.Model;

@Controller
public class UserController {

    public UserController() {
    }

    @GetMapping(value = {"/user"})
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String user(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model) {
        model.addAttribute("user", principal);
        return "user";
    }
}