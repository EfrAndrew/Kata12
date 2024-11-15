package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    Role findRoleByName(String name);

    void saveRole(Role role);

    List<Role> getAllRoles();
}