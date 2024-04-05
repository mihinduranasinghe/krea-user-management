package com.example.kreausermanagement.dto.response.error;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserStatusErrorResponse {
    private String name;
    private String message;
    private Timestamp timestamp;
}