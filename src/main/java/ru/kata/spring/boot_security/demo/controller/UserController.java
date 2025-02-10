package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;

import java.util.List;

@Controller
public class UserController {

    private final MyUserService myUserService;

    @Autowired
    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/admin/users-list")
    public String showAllUsers(Model model) {
        List<User> allUsers = myUserService.findAll();
        model.addAttribute("users", allUsers);
        return "users-list";
    }

    @GetMapping("/admin/addUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        myUserService.save(user);
        return "redirect:/admin/users-list";
    }

    @GetMapping("/admin/updateUser")
    public String updateUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", myUserService.findById(id));
        return "update-user";
    }

    @PostMapping("/admin/saveUpdatedUser")
    public String saveUpdatedUser(@ModelAttribute("user") User user) {
        myUserService.save(user);
        return "redirect:/admin/users-list";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        myUserService.deleteById(id);
        return "redirect:/admin/users-list";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userInform", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<User> allUsers = myUserService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }
}
