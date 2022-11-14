package com.example.project1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateDataDTO {
    private String account;
    private String accountNumber;
    private UserPersonalDetailsDTO userDetails;
}
