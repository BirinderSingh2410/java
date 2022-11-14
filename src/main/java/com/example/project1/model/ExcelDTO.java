package com.example.project1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExcelDTO {
    private String billcategory;
    private String itemName;
    private String tarifftype;
    private String cost;
    private int vitrayaAmount;
    private String department;
}
