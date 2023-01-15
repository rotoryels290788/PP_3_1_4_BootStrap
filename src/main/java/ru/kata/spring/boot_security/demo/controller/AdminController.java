package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RolesService rolesService;

    @Autowired
    public AdminController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    @GetMapping("/newUserAdmin")
    public String addNewUser(User user,Model model) {
        model.addAttribute("newUser", user);
        model.addAttribute("roleList", rolesService.getList());

        return "edit_bootstrap";
    }

    @PostMapping("/newUserAdmins")
    public String saveNewUser(User user) {
        List<String> list = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
        List<Roles> roles = rolesService.listByName(list);
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin/newUserAdmin";
    }
    @GetMapping("/newUserAdmins/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", rolesService.getList());
        return "newUserAdmin";
    }


    @GetMapping("/delete")
    public String delete(Model model) {
        model.addAttribute("users", userService.getList());
        return "delete_bootstrap";
    }
    @GetMapping("/start")
    public String index() {
        return "start_page";
    }
    @GetMapping
    public String users(Model model, Principal principal) {
        User user = userService.getEmail(principal.getName());
        model.addAttribute("messages", user);
        model.addAttribute("use", new User());
        model.addAttribute("users", userService.getList());
        model.addAttribute("roleList", rolesService.getList());
        return "users";
    }
    @GetMapping("/newUsers")
    public String createUserForm(User user, Model model) {
        model.addAttribute("roleList", userService.getList());
        return "newUsers";
    }

    @PostMapping("/newUsers")
    public String userSaveEdit(User user) {
        if (user.getRoles() != null) {
            List<String> list = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
            List<Roles> roles = rolesService.listByName(list);
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", rolesService.getList());
        return "editUsers";
    }

    @PostMapping("/editUsers")
    public String edits(User user) {
        if (user.getRoles() != null) {
            List<String> list = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
            List<Roles> roles = rolesService.listByName(list);
            user.setRoles(roles);
            userService.editUser(user);
        }
        userService.editUser(user);
        return "redirect:/admin";
    }


}
