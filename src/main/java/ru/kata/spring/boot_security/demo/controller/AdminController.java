package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleDao roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleDao roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String listUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByName(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roleNames") String[] selectedRoles) {
        Set<Role> roles = new HashSet<>();
        Arrays.stream(selectedRoles)
                .forEach(a -> roles.add(roleRepository.findRoleByName(a)));
        user.setRoles(roles);
        userService.saveUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roleNames") String[] selectedRoles) {
        Set<Role> roles = new HashSet<>();
        Arrays.stream(selectedRoles)
                .forEach(a -> roles.add(roleRepository.findRoleByName(a)));
        user.setRoles(roles);
        userService.updateUser(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/find")
    public String findUserById(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findUserByName(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        User user = userService.findUserById(id);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("foundUser", user);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin";
    }
}
