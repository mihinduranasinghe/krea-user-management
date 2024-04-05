package com.example.kreausermanagement.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserCredentials {
    private String username;
    private String password;
}
