package com.example.project1.service;

import com.example.project1.dao.LoginDao;
import com.example.project1.entity.LoginEntity;
import com.example.project1.model.ApiResponse;
import com.example.project1.model.DataByName;
import com.example.project1.model.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;


@Service
public class LoginService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private EncryptData token;

    @Autowired
    private ApiResponseService apiService;

    public boolean checkUSerExist(String auth) throws Exception {
        try{
           String name  = loginDao.checkUserExist(auth);
           if(name == null){
               System.out.println("Invalid User");
               return false;
           }
           return true;
        }
        catch(Exception e){
            throw new Exception("Something Went Wrong");
        }
    }
    public ApiResponse signup(LoginDTO data) throws Exception {

        try{
            String passwordToken = token.createToken(data.getPassword());
            loginDao.signUpData(data.getId(),data.getFirstname(), data.getLastname(),passwordToken);
            return apiService.setResponse(true,"User Created",null);
        }
        catch(Exception e){
            throw new Exception("Something Went Wrong");
        }
    }

    public ApiResponse signin(DataByName requestdata) throws Exception {

        try{
            EncryptData token = new EncryptData();
            String webtoken = loginDao.getToken(requestdata.getName());
            boolean istoken = token.checkuser(webtoken,requestdata.getPassword());
            LoginEntity data=null;
            if(istoken){
                data = loginDao.fetchDataByName(requestdata.getName());
                return apiService.setResponse(true,"User Sign In",data);
            }
            return apiService.setResponse(true,"Wrong Password",data);
        }
        catch(Exception e){
            throw new Exception("Something Went Wrong");
        }

    }

    public ApiResponse forgetPassword(String name, String password, int otp, HttpSession session) throws Exception {
        try{
            String passwordToken = token.createToken(password);
            int ischanged =  loginDao.changePassword(name,passwordToken);
            int sessionOtp = (int) session.getAttribute("userotp");
            if(ischanged == 1 && sessionOtp == otp){
                mailPasswordChanged(name);
                return apiService.setResponse(true,"Password Changed",null);
            }
            return apiService.setResponse(true,"User Not Found",null);
        }
        catch(Exception e){
            throw new Exception("Something Went Wrong");
        }
    }

    public ApiResponse mailOtpSend(int otp) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("inder2000523@gmail.com");
        message.setTo("bir90220@gmail.com");
        message.setSubject("Request For Change Password");
        message.setText("Hi Birinder,"+"\n"+"\n"+"OTP for request of password change:"+otp+"\n"+"\n"+"Regards,"+"\n"+"Practice demo");
        try {
            mailSender.send(message);
            return apiService.setResponse(true,"Mail sent",null);
        }
        catch (Exception e) {
            throw new Exception("Something Went Wrong");
        }
    }

    public ApiResponse mailPasswordChanged(String name) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("inder2000523@gmail.com");
        message.setTo("bir90220@gmail.com");
        message.setSubject("Request For Change Password");
        message.setText("Hi Birinder,"+"\n"+"\n"+"Username:"+name+" has changed its password"+"\n"+"\n"+"Regards,"+"\n"+"Practice demo");
        System.out.println("Mail data set");
        try {
            mailSender.send(message);
            return apiService.setResponse(true,"Mail sent",null);
        }
        catch (Exception e) {
            throw new Exception("Something Went Wrong");
        }
    }

    public ApiResponse setOtp(HttpSession session) throws Exception {
        try{
            Random random = new Random();
            int otp = random.nextInt(999999);
            session.setAttribute("userotp",otp);
            mailOtpSend(otp);
            return apiService.setResponse(true,"OTP has sent to requested mail",otp);
        }
        catch(Exception e){
            throw new Exception("Something went wrong while sending OTP");
        }
    }
}
