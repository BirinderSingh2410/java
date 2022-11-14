package com.example.project1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
}
