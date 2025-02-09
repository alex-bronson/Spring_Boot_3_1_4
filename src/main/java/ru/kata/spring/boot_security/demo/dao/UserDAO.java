package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Component
public interface UserDAO {
    List<User> findAll();
    User findById(int id);
    void save(User user);
    void updateUser(User user);
    void deleteById(int id);
    User findByUsername (String username);
}
