package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User findUserById(Long Id);

    User findUserByName(String username);

    List<User> getAllUsers();

    User findUserWithRolesByUsername(String username);
}
