package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.common.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestResponseData {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;

    private ResponseStatus status;
}
