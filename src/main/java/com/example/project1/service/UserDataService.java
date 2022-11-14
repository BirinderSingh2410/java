package com.example.project1.service;

import com.example.project1.dao.LoginDao;
import com.example.project1.dao.UserDataDao;
import com.example.project1.entity.LoginEntity;
import com.example.project1.entity.UserDataEntity;
import com.example.project1.model.ApiResponse;
import com.example.project1.model.UpdateDataDTO;
import com.example.project1.model.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserDataService {
    @Autowired
    private UserDataDao userDao;

    @Autowired
    private LoginDao loginDao;

    @Autowired
    private ApiResponseService apiService;
    public ApiResponse updatedata(UpdateDataDTO user, String name) throws Exception {
        try{
            LoginEntity userData = loginDao.findByFirstname(name);
            UserDataEntity userDataEntity = new UserDataEntity();

            Map<String,Object> bankDetails = new HashMap();
            Map<String,Object> userDetails = new HashMap();

            bankDetails.put("account",user.getAccount());
            bankDetails.put("accountNumber",user.getAccountNumber());

            userDetails.put("contactNumber",user.getUserDetails().getContactNumber());
            userDetails.put("address",user.getUserDetails().getAddress());



            bankDetails.put("userDetails",userDetails);

            Gson gsondata = new GsonBuilder().create();
            String jsondata = gsondata.toJson(bankDetails);

            userDataEntity.setId(userData.getId());
            userDataEntity.setData(jsondata);

            userDao.save(userDataEntity);
            return apiService.setResponse(true,"Data is saved",null);
        }
        catch(Exception e){
            throw new Exception("Something Went Wrong");
        }
    }

    public ApiResponse getUserData(int id) throws Exception {
        try{
            UserDataEntity userData = null;
            userData = userDao.getUserData(id);

            Gson gsonBuilder = new GsonBuilder().create();
            UpdateDataDTO userdetails = gsonBuilder.fromJson(userData.getData(),UpdateDataDTO.class);
            return apiService.setResponse(true,"Data recieved Successfully",userdetails);
        }
        catch (Exception e){
            throw new Exception("Something Went Wrong");
        }
    }

}
