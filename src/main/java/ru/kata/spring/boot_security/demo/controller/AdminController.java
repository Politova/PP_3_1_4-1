package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.*;

import java.util.List;


@Controller
public class AdminController {
    @Autowired
    private UserServiceImp userService;

    @Autowired
    private RoleService roleService;
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String displayAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "admin";
    }
    @GetMapping("/admin/addUser")
    @Secured("ROLE_ADMIN")
    public String displayNewUserForm(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("headerMessage", "Добавить пользователя");
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/admin/editUser")
    @Secured("ROLE_ADMIN")
    public String updateUsers(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
        userService.getUserAndRoles(user, roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editUser")
    @Secured("ROLE_ADMIN")
    public String displayEditUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("headerMessage", "Изменить пользователя");
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/addUser")
    @Secured("ROLE_ADMIN")
    String create(@ModelAttribute("user") User user, @RequestParam(name = "roles", required = false) List<Long> roleId) {
        userService.getNotNullRole(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteUser")
    @Secured("ROLE_ADMIN")
    public String deleteUserById(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

}