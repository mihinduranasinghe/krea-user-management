package com.example.kreausermanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginRequest {
    @JsonProperty("email")
    @Schema(example = "mihinduranasinghe@gmail.com", required = true)
    private String email;

    @JsonProperty("password")
    @Schema(example = "********", required = true)
    private String password;
}
