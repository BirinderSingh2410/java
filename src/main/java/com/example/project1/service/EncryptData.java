package com.example.project1.service;

import com.example.project1.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptData {

    @Autowired
    private LoginDao loginDao;

    private PasswordEncoder pass =  new BCryptPasswordEncoder();

    public boolean checkuser(String passwd,String password){

        return pass.matches(password, passwd);
    }

    public String createToken(String password){
        PasswordEncoder pass = new BCryptPasswordEncoder();

        String passwd = pass.encode(password);

        return passwd;
    }

}
