package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.dto.response.error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponse {
    private UserUpdateResponseData data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorResponse error;
}