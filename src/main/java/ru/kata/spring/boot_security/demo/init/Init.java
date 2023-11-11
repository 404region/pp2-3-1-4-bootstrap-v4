package ru.kata.spring.boot_security.demo.init;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class Init {
    UserService userService;
    RoleService roleService;

    PasswordEncoder passwordEncoder;

    @Autowired
    public Init(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @PostConstruct
    public void run() {

        roleService.addRole(new Role("ROLE_ADMIN"));
        roleService.addRole(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(roleService.getRoleById(1L));
        userRole.add(roleService.getRoleById(2L));

        userService.updateUser(new User("Ann", "S", "admin", 99, passwordEncoder.encode("user"), adminRole));
        userService.updateUser(new User("Mary", "N" , "user1",115,  passwordEncoder.encode("user"), userRole));
        userService.updateUser(new User("Taylor", "S", "user2", 12,  passwordEncoder.encode("user"), userRole));
        userService.updateUser(new User("Cat", "N", "user3",3,  passwordEncoder.encode("user"), userRole));

    }
}
