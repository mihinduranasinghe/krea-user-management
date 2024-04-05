package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.common.enums.ResponseStatus;
import com.example.kreausermanagement.dto.response.error.UserStatusErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailResponse {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_details")
    private UserResponsePublicData user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserStatusErrorResponse error;
}