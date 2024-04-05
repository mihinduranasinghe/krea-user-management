package com.example.kreausermanagement.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class UserRequest {

    @JsonProperty("name")
    @Schema(example = "Mihindu Ranasinghe", required = true)
    private String userName;

    @JsonProperty("occupation")
    @Schema(example = "Software Engineer", required = true)
    private String occupation;

    @JsonProperty("address")
    @Schema(example = "Colombo 03", required = true)
    private String address;

    @JsonProperty("email")
    @Schema(example = "mihinduranasinghe@gmail.com", required = true)
    private String email;

    @JsonProperty("password")
    @Schema(example = "********", required = true)
    private String password;
}

