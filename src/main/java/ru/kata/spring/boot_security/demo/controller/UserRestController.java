package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final MyUserService myUserService;

    @Autowired
    public UserRestController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(myUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public  ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user = myUserService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody User updatedUser) {
        User existingUser = myUserService.findById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());

        myUserService.save(existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @PostMapping("/addNewUser")
    public void addNewUser(@RequestBody User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newCode = passwordEncoder.encode(user.getPassword());
        user.setPassword(newCode);
        myUserService.save(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        myUserService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
