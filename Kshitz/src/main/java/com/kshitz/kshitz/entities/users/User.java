package com.kshitz.kshitz.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshitz.kshitz.audits.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    @JsonIgnore
private String password;
    @JsonIgnore
    private Boolean isDeleted;

    private Boolean isActive;
    @JsonIgnore
    private Integer attempts=0;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userid",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
            List<Role> role;



    public User(){

   }
   @PrePersist
   public void onPrePersist(){
        create();
   }
    @PreUpdate
    public void onPreUpdate() {
        audit();
    }
    @PreRemove
    public void onPreDelete(){
        audit();
    }
    private void audit() {
        setLastModifiedBy(username);
        setLastModifiedDate(new Date());
    }
    private void create(){
        setCreatedBy(username);
        setLastModifiedBy(username);
        setLastModifiedDate(new Date());
        setCreatedDate(new Date());
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
