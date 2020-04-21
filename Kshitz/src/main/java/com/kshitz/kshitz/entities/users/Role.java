package com.kshitz.kshitz.entities.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roles;

    public Role(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String role) {
        this.roles = role;
    }

    public Role(Long id, String role) {
        this.id = id;
        this.roles = role;
    }
}
