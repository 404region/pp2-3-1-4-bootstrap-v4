package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Transient
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> userSet = new HashSet<>();


    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    @Override
    public String toString() {
        if (roleName.equals("ROLE_ADMIN")) {
            return "ADMIN";
        } else {
            return "USER";
        }
    }

}
