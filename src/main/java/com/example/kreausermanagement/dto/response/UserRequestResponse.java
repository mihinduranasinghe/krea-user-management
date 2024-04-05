package com.example.kreausermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestResponse {
    private UserRequestResponseData data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorResponse error;
}