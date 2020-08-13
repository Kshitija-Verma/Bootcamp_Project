package com.kshitz.kshitz.entities.users;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {

    private String id;
    private String roles;

    public Role(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String role) {
        this.roles = role;
    }

    public Role(String  id, String role) {
        this.id = id;
        this.roles = role;
    }
}
