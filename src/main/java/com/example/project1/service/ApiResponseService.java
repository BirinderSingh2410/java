package com.example.project1.service;

import com.example.project1.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseService {

    public ApiResponse setResponse(boolean status, String message, Object data){
        ApiResponse apistatus = new ApiResponse();
        apistatus.setMessage(message);
        apistatus.setSuccess(status);
        apistatus.setData(data);
        return apistatus;
    }
}
