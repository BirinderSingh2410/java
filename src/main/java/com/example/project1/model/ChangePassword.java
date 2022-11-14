package com.example.project1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePassword {

    private String password;
    private int otp;
}
