package com.example.kreausermanagement.service;

import com.example.kreausermanagement.dto.request.LoginRequest;
import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.AuthenticationResponse;

public interface IAuthenticationService {
    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param request - Krea User Management Job related information
     * @return
     */
    AuthenticationResponse register(UserRequest request);

    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param request - Krea User Management Job related information
     * @return
     */
    AuthenticationResponse authenticate(LoginRequest request);
}
