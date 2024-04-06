package com.example.kreausermanagement.service;

import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.UserDetailResponse;
import com.example.kreausermanagement.dto.response.UserCreateResponse;
import com.example.kreausermanagement.dto.response.UserUpdateResponse;
import com.example.kreausermanagement.dto.response.UsersResponse;
import com.example.kreausermanagement.entity.User;

/**
 * This service is used process job by ids
 */
public interface IUserService {

    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param jobRequest - Krea User Management Jobs related information
     * @return
     */
    UserCreateResponse addUserDetails(UserRequest jobRequest);

    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param jobRequest - Krea User Management Jobs related information
     * @return
     */
    UserUpdateResponse updateUserDetails(Long id, UserRequest jobRequest);

    /**
     * This service is used to process the job request from Krea User Management
     * @return
     */
    UsersResponse getUsers();

    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param jobId - Krea User Management Jobs related information
     * @return
     */
    UserDetailResponse getUserDetails(Long jobId);

    /**
     * This service is used to process the job request from Krea User Management
     *
     * @param jobId - Krea User Management Jobs related information
     * @return
     */
    UserDetailResponse removeUserDetails(Long jobId);
}
