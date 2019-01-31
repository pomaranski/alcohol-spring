package com.ppbkaf.application.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class User implements Copiable<User>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 45)
    private String login;
    @Column(nullable = false, length = 300)
    private String password;
    private boolean isAdmin;
    private boolean isActivated;
    @Email
    @Column(nullable = false, length = 45)
    private String email;
    @Column(nullable = false, length = 20)
    private String role;

    @Override
    public void copy(User obj) {
        this.setAdmin(obj.isAdmin());
        this.setLogin(obj.getLogin());
        this.setPassword(obj.getPassword());
        this.setAdmin(obj.isAdmin());
        this.setActivated(obj.isActivated());
        this.setEmail(obj.getEmail());
    }
}
