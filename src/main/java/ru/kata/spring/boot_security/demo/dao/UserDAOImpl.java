package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> findAll() {
        List<User> allUsers = entityManager.createQuery("select u from User u ", User.class)
                .getResultList();
        return allUsers;
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(int id) {
        entityManager.createQuery("delete from User user WHERE user.id =:id")
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public User findByUsername(String username) {
        return entityManager
                .createQuery("select user from User user where user.username =: username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
