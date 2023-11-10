
package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private static final String REDIRECT = "redirect:/admin";

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
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
        // Checking validation exception
        if (bindingResult.hasErrors()) {

            model.addAttribute("allRoles", roleService.getAllRoles());
            Set<Role> assignedRole = roleService.findAllRoleId(ids);
            user.setRoles(assignedRole);

            return "create";
        } else {
            try {
                Set<Role> assignedRole = roleService.findAllRoleId(ids);
                user.setRoles(assignedRole);
                userService.updateUser(user);
                return REDIRECT;
            } catch (DataIntegrityViolationException e) {
                bindingResult.rejectValue("username", "duplicate", "This is username is already taken");
                model.addAttribute("allRoles", roleService.getAllRoles());
                Set<Role> assignedRole = roleService.findAllRoleId(ids);
                user.setRoles(assignedRole);
                return "create";
            }
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return REDIRECT;
    }

    @GetMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult
            , Model model , @RequestParam List<Long> ids) {
        System.out.println("ids "+ ids);
        if (bindingResult.hasErrors()) {

            model.addAttribute("allRoles", roleService.getAllRoles());
            Set<Role> assignedRole = roleService.findAllRoleId(ids);
            user.setRoles(assignedRole);

            return "edit";
        } else {
            try {
                Set<Role> assignedRole = roleService.findAllRoleId(ids);
                user.setRoles(assignedRole);
                userService.updateUser(user);
                return REDIRECT;
            } catch (DataIntegrityViolationException e) {
                bindingResult.rejectValue("username", "duplicate", "This is username is already taken");
                model.addAttribute("allRoles", roleService.getAllRoles());
                Set<Role> assignedRole = roleService.findAllRoleId(ids);
                user.setRoles(assignedRole);
                return "edit";
            }
        }
    }
}
