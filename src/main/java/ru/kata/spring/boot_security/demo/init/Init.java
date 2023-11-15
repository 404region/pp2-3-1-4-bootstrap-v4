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
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @PostConstruct
    public void run() {

        Role adminRole1 = new Role("ROLE_ADMIN");
        Role userRole1 = new Role("ROLE_USER");

        roleService.addRole(adminRole1);
        roleService.addRole(userRole1);

        Set<Role> adminRole = new HashSet<>(Set.of(adminRole1));
        Set<Role> userRole = new HashSet<>(Set.of(userRole1));

        userService.addUser(new User("Ann", "S", "admin", "d@d.ru", 99, "user", adminRole));
        userService.addUser(new User("Mary", "N" , "user1","u1@d.ru", 115,  "user", userRole));
        userService.addUser(new User("Taylor", "S", "user2", "u2@d.ru",12, "user",  userRole));
        userService.addUser(new User("Cat", "N", "user3","u2@d.ru", 3,  "user", userRole));

    }
}
