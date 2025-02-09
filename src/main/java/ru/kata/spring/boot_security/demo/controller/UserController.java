package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;

import java.util.List;

@Controller
public class UserController {


    private final MyUserService myUserService;

    @Autowired
    public UserController(MyUserService userService) {
        this.myUserService = userService;
    }

    @GetMapping("/users-list")
    public String showAllUsers(Model model) {
        List<User> allUsers = myUserService.findAll();
        model.addAttribute("users", allUsers);
        return "users-list";
    }
    @GetMapping("/addUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        myUserService.save(user);
        return "redirect:/users-list";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", myUserService.findById(id));
        return "update-user";
    }

    @PostMapping("/saveUpdatedUser")
    public String saveUpdatedUser(@ModelAttribute("user") User user) {
        myUserService.save(user);
        return "redirect:/users-list";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id){
        myUserService.deleteById(id);
        return "redirect:/users-list";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userInform",user.toString());
        return "user";
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        List<User> allUsers = myUserService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }
}
