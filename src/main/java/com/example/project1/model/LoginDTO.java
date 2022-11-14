package com.example.project1.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDTO {


    private int id;


    private String password;


    private String firstname;


    private String lastname;
}
