package com.example.project1.controller;

import com.example.project1.dao.UserDataDao;
import com.example.project1.model.*;
import com.example.project1.service.ExcelService;
import com.example.project1.service.LoginService;
import com.example.project1.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class LoginController{

    @Autowired
    public HttpSession session;


    @Autowired
    public ExcelService excelService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDataService userService;

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody LoginDTO data)throws Exception{
        return loginService.signup(data);
    }

    @GetMapping ("/signin")
    public ApiResponse getLastname(@RequestBody DataByName requestdata)throws Exception{
        return loginService.signin(requestdata);
    }

    @PostMapping("/forget/{name}")
    public ApiResponse forgetPassword(@PathVariable String name, @RequestBody ChangePassword password)throws Exception{
        System.out.println(password);
        return loginService.forgetPassword(name,password.getPassword(),password.getOtp(),session);
    }

//    @GetMapping("/getdata/{name}")
//    public LoginEntity dataByName(@PathVariable String name) {
//        LoginEntity data;
//        data = loginDao.findByFirstname(name);
//        return data;
//    }

//    @PostMapping("/update")
//    public ApiResponse update(){
//        return loginService.mailSend("Birinder");
//    }

    @PostMapping("/putdata/{name}")
    public ApiResponse putData(@RequestBody UpdateDataDTO user , @PathVariable String name)throws Exception{
        return userService.updatedata(user,name);
    }
//
    @GetMapping("/getdata/{id}")
    public ApiResponse getData(@PathVariable int id)throws Exception{
        return userService.getUserData(id);
    }

    @PostMapping("/sendotp")
    public ApiResponse sendotp() throws Exception {
        return loginService.setOtp(session);
    }

    @GetMapping("/excelsheet")
    public ApiResponse getExcel(HttpServletResponse response)throws Exception {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=User_info.xlsx";

        response.setHeader(headerKey,headerValue);
        return excelService.export(response);
    }

    @GetMapping("/excelsheet/getdata")
    public ApiResponse getexcelSheet() throws Exception{
        return excelService.importSheet();
    }
}
