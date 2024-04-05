package com.example.kreausermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseData {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;
}
