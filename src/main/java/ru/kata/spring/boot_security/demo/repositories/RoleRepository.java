package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

//Слой Дао
public interface RoleRepository  extends JpaRepository<Role, Long> {
}
