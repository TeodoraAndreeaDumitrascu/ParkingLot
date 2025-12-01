package com.parking.parkinglot.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usergroups")
public class UserGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "usergroup")
    private String userGroup;

    // Constructori
    public UserGroup() {
    }

    public UserGroup(String username, String userGroup) {
        this.username = username;
        this.userGroup = userGroup;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}