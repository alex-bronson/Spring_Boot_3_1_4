package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MyUserServiceImpl implements MyUserService {
    private UserDAO userDAO;

    @Autowired
    public MyUserServiceImpl(UserDAO userDAO) {
        this.userDAO=userDAO;
    }

    @Transactional
    public List<User> findAll(){
        return userDAO.findAll();
    }

    @Transactional
    public User findById(int id){
        return userDAO.findById(id);
    }

    @Transactional
    public void save(User user){
        userDAO.save(user);
    }

    @Transactional
    public void deleteById(int id){
        userDAO.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username);
    }
}
