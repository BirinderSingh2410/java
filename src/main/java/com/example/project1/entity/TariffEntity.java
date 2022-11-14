package com.example.project1.entity;

import com.example.project1.model.CostsDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tariff")
@Getter
@Setter
@ToString
public class TariffEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name="billCategory")
    private String billCategory;

    @Column(name="itemName")
    private String itemName;

    @Column(name = "tariffType")
    private String tariffType;

    @Column(name = "cost")
    private String cost;

    @Column(name = "vitrayaAmount")
    private int vitrayaAmount;

    @Column(name = "department")
    private String department;
}
