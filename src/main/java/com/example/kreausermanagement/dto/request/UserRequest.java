package com.example.kreausermanagement.dto.request;


import com.example.kreausermanagement.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}

