package com.example.project1.dao;

import com.example.project1.entity.LoginEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LoginDao extends CrudRepository<LoginEntity, Long>  {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user(id,firstname,lastname,token) VALUES (:id,:firstname,:lastname,:token)",nativeQuery = true)
    void signUpData(@Param("id") int id, @Param("firstname")String firstname, @Param("lastname")String lastname, @Param("token")String token);

    @Query(value="SELECT * FROM user where firstname = :name",nativeQuery = true)
    LoginEntity fetchDataByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value="UPDATE user SET token= :token WHERE firstname= :firstname",nativeQuery = true)
    int changePassword(@Param("firstname") String firstname,@Param("token") String token);

    @Query(value = "SELECT token from user where firstname= :name",nativeQuery = true)
    String getToken(@Param("name") String name);

    LoginEntity findByFirstname(String name);

    @Query(value = "Select firstname from user where token= :token",nativeQuery = true)
    String checkUserExist(@Param("token")String token);
//    @Query(value = "SELECT * FROM user where firstname= :name",nativeQuery = true)
//    loginEntity fetchDataByName(@Param("name") String name);
//
//    LoginEntity findByFirstname(String name);

    @Query(value = "Select * from user",nativeQuery = true)
    List<LoginEntity> getAllData();
}
