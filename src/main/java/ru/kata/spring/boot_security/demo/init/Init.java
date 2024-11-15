package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleDao roleRepository;
    private final UserDao userDao;

    @Autowired
    public Init(UserService userService, RoleDao roleRepository, UserDao userDao) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userDao = userDao;
    }

    @PostConstruct
    public void initUsers() {
        if (roleRepository.findRoleByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.saveRole(adminRole);
        }
        if (roleRepository.findRoleByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.saveRole(userRole);
        }

        if (userService.findUserByName("admin") == null) {
            Set<Role> adminRoles = new HashSet<>();
            Role userRole = roleRepository.findRoleByName("ROLE_USER");
            Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            userService.saveUser("admin", "admin@kata.ru", "admin", adminRoles);
        }

        if (userService.findUserByName("user") == null) {
            Set<Role> userRoles = new HashSet<>();
            Role role = roleRepository.findRoleByName("ROLE_USER");
            userRoles.add(role);
            userService.saveUser("user", "user@kata.ru", "user", userRoles);
        }
    }


}