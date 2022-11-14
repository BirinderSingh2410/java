package com.example.project1.dao;

import com.example.project1.entity.LoginEntity;
import com.example.project1.entity.TariffEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.transaction.Transactional;

@Repository
public interface TariifDao extends CrudRepository<TariffEntity, Long> {


    @Transactional
    @Modifying
    @Query(value = "Insert into tariff(billCategory,itemName,tariffType,cost,vitrayaAmount,department) values(:billcategory,:itemName,:tariffType,:cost,:vitrayaAmount,:department)",nativeQuery = true)
    void insertTariff( @Param("billcategory") String billcategory,@Param("itemName")String itemName,@Param("tariffType")String tariffType,@Param("cost")String cost,@Param("vitrayaAmount")int vitrayaAmount,@Param("department")String department);
}
