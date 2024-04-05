package com.example.kreausermanagement.dto.response.error;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ErrorDetail {
    private int code;
    private String message;
    private String field;
    private String value;
    private String location;
}

