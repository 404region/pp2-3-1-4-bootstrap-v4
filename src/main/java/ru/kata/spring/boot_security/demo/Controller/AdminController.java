
package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@Valid
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private final String REDIRECT = "redirect:/admin";

    @Autowired
    public AdminController(RoleService roleService, UserService userService, PasswordEncoder  passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "create";
    }

    @PostMapping(value = "/new")
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult
            , Model model, @RequestParam List<Long> ids) {
            Set<Role> assignedRole = roleService.findAllRoleId(ids);
            user.setRoles(assignedRole);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.updateUser(user);
            return REDIRECT;
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return REDIRECT;
    }

    @GetMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.getAllRoles();
        Set<Role> userRoles = user.getRoles();

        for (Role role : allRoles) {
            if (userRoles.contains(role)) {
                role.setActive(true);
            } else {
                role.setActive(false);
            }
        }

        System.out.println("assignedRole "+allRoles);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult
            , Model model , @RequestParam List<Long> ids) {
            Set<Role> assignedRole = roleService.findAllRoleId(ids);
            user.setRoles(assignedRole);
            userService.updateUser(user);
            return REDIRECT;
    }
}
