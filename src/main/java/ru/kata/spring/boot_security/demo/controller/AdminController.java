
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
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

    @GetMapping
    public String getUserPage(Principal principal, Model model) {
        System.out.println("Попали в метод getAllUsers");
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("noAdminRole", !user.isHasAdminRole());
        return "admin-user-page";
    }

    @PostMapping("/addNewUser")
    public String addUser(@ModelAttribute("user") User user) {
        System.out.println("Попали в метод addUser");
        userService.addUser(user);
        return "redirect:/admin/";
    }

    /*@GetMapping(value = "")
    public String getAllUsers(Principal principal, Model model) {
        System.out.println("Попали в метод getAllUsers");
        User user = userService.getUserByUsername(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("noAdminRole", !user.isHasAdminRole());
        return "admin-user-page";
    }



    @GetMapping(value = "/admin/{id}")
    public String test(@PathVariable("id") Long id, Model model) {
        System.out.println("Попали в метод test");

        return "admin-user-page";
    }


    @GetMapping("/addNewUser")
    public String showCreateUserForm(ModelMap model) {
        System.out.println("Попали в метод showCreateUserForm класса AdminController");
        User user = new User();
        Collection<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "new-user-info";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user) {
        System.out.println("Попали в метод корень класса AdminController");
        userService.addUser(user);
        return REDIRECT;
    }

    @GetMapping(value = "/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        System.out.println("Попали в метод getUserById класса AdminController");
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        System.out.println("Попали в метод addUser класса AdminController");
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin-user-page";
    }

    @PostMapping(value = "/new")
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult
            , Model model, @RequestParam List<Long> ids) {
        System.out.println("Попали в метод add класса AdminController");
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        System.out.println("Попали в метод delete класса AdminController");
        userService.removeUser(id);
        return REDIRECT;
    }

    @GetMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        System.out.println("Попали в метод updateUser класса AdminController");
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.getAllRoles();

        System.out.println("assignedRole "+allRoles);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult
            , Model model) {
        System.out.println("Попали в метод update класса AdminController");
        //Set<Role> assignedRole = roleService.findAllRoleId(ids);
        //user.setRoles(assignedRole);
        userService.updateUser(user);
        return REDIRECT;
    }*/

}
