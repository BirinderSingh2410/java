package com.example.project1.dao;

import com.example.project1.entity.UserDataEntity;
import org.apache.catalina.User;
//import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDataDao extends CrudRepository<UserDataEntity, Long> {

    @Query(value = "Select * from userdata where id= :id",nativeQuery = true)
    UserDataEntity getUserData(@Param("id")int id);

    @Query(value = "Select * from userdata",nativeQuery = true)
    List<UserDataEntity> getAllData();

}
