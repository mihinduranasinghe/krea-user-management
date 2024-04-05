package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.common.enums.ResponseStatus;
import com.example.kreausermanagement.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsersResponse {
    @JsonProperty("users")
    private List<User> users;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserStatusErrorResponse error;
}