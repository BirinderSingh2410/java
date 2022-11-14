package com.example.project1.entity;

import com.example.project1.model.UpdateDataDTO;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userdata")
@Getter
@Setter
public class UserDataEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name="data")
    private String data;
}
