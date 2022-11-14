package com.example.project1.interceptor;

import com.example.project1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProjectInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Auth");
        if(StringUtils.isEmpty(auth)){
            throw new Exception("Token is Empty");
        }
        if(!loginService.checkUSerExist(auth)){
            throw new Exception("Invalid Token");
        }
        return true;
    }


}
