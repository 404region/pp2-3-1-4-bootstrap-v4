package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;


public interface RoleRepository {
    Role getById(Long id);

    void save(Role role);

    List<Role> findAll();

    Set<Role> findAllId(List<Long> ids);

}