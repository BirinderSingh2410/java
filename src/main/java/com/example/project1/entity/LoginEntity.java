package com.example.project1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class LoginEntity {
    @Id
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "firstname")
    private String firstname;

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "lastname")
    private String lastname;

    public String getToken() {
        return token;
    }

    @Column(name="token")
    private String token;

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

