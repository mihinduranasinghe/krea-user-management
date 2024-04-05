package com.example.kreausermanagement.service.impl;

import com.example.kreausermanagement.common.Constants;
import com.example.kreausermanagement.common.enums.Error;
import com.example.kreausermanagement.common.enums.ResponseStatus;
import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.*;
import com.example.kreausermanagement.dto.response.error.ErrorResponse;
import com.example.kreausermanagement.dto.response.error.UserStatusErrorResponse;
import com.example.kreausermanagement.entity.Role;
import com.example.kreausermanagement.entity.User;
import com.example.kreausermanagement.exception.RestException;
import com.example.kreausermanagement.repository.IRoleRepository;
import com.example.kreausermanagement.repository.IUserRepository;
import com.example.kreausermanagement.service.IUserService;
import com.example.kreausermanagement.utils.ApiValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

@Slf4j
@EnableAutoConfiguration
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRequestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    ApiValidator apiValidator;

    @Value("${krea.ums.api.documentation}")
    private String apiDocumentation;

    @Override
    public UserCreateResponse addUserDetails(UserRequest request) {
        UserCreateResponse response = UserCreateResponse.builder().build();
        try {
            log.info("User request received to create a new user : {}", request);
            Role role = roleRepository.findByName(Constants.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            User user = User.builder()
                    .name(request.getUserName())
                    .address(request.getAddress())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .occupation(request.getOccupation()).build();

            User saveResponse = userRequestRepository.save(user);
            log.info("User saved successfully with the user ID : {}", saveResponse.getUserId());
            response.setData(createUserPublicInfo(saveResponse));
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (RestException ex) {
            response.setStatus(ResponseStatus.FAILED);
            response.setError(ErrorResponse.builder()
                    .code(ex.getCode())
                    .message(ex.getMessage())
                    .field(Constants.ORDER_DATE)
                    .value(ex.getAdditionalInfo())
                    .informationLink(apiDocumentation)
                    .build());
        } catch (Exception ex) {
            log.error("Exception occurred while submitting user : {}", ex.getMessage(), ex);
            response.setError(ErrorResponse.builder()
                    .code(Error.INTERNAL_SERVER_ERROR.getCode())
                    .message(Error.INTERNAL_SERVER_ERROR.getMessage())
                    .informationLink(apiDocumentation)
                    .build());
        }
        return response;
    }

    @Override
    public UserUpdateResponse updateUserDetails(Long id, UserRequest request) {
        UserUpdateResponse response = UserUpdateResponse.builder().build();
        try {
            log.info("User request received to update user : {}", request);
            apiValidator.validateJobId(id, true);
            Optional<User> existingUser = userRequestRepository.findByUserId(id);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setName(request.getUserName());
                user.setAddress(request.getAddress());
                user.setEmail(request.getEmail());
                user.setOccupation(request.getOccupation());
                User saveResponse = userRequestRepository.save(user);
                log.info("User saved successfully with the user ID : {}", saveResponse.getUserId());
                response.setData(UserUpdateResponseData.builder()
                        .status(ResponseStatus.SUCCESS).build());
            } else {
                log.error("There is no user found with the ID : {}", id);
                response.setError(ErrorResponse.builder()
                        .code(Error.USER_ID_INVALID_ERROR.getCode())
                        .message(Error.USER_ID_INVALID_ERROR.getMessage())
                        .informationLink(apiDocumentation)
                        .build());
            }
        } catch (RestException ex) {
            response.setData(UserUpdateResponseData.builder().status(ResponseStatus.FAILED).build());
            response.setError(ErrorResponse.builder()
                    .code(ex.getCode())
                    .message(ex.getMessage())
                    .field(Constants.ORDER_DATE)
                    .value(ex.getAdditionalInfo())
                    .informationLink(apiDocumentation)
                    .build());
        } catch (Exception ex) {
            log.error("Exception occurred while updating user : {}", ex.getMessage(), ex);
            response.setError(ErrorResponse.builder()
                    .code(Error.INTERNAL_SERVER_ERROR.getCode())
                    .message(Error.INTERNAL_SERVER_ERROR.getMessage())
                    .informationLink(apiDocumentation)
                    .build());
        }
        return response;
    }

    @Override
    public UsersResponse getUsers() {
        UsersResponse userDetailResponse = UsersResponse.builder().build();
        try {
            log.info("Retrieving all user");
            List<User> kreaUsers = userRequestRepository.findAll();
            log.info("Users retrieved successfully");
            List<UserResponsePublicData> userResponsePublicDataList = kreaUsers.stream()
                    .map(UserService::createUserPublicInfo)
                    .collect(Collectors.toList());
            userDetailResponse.setUsers(userResponsePublicDataList);
            userDetailResponse.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving users with error : {}", ex.getMessage(), ex);
            userDetailResponse.setError(UserStatusErrorResponse.builder()
                    .name(Constants.INVALID_REQUEST)
                    .message(Constants.INTERNAL_SERVER_ERROR_MESSAGE)
                    .timestamp(Timestamp.valueOf(LocalDateTime.now())).build());
        }
        return userDetailResponse;
    }

    @Override
    public UserDetailResponse getUserDetails(Long id) {
        UserDetailResponse userDetailResponse = UserDetailResponse.builder().userId(id).build();
        try {
            log.info("Retrieving user for the user Id : {}", id);
            apiValidator.validateJobId(id, true);
            Optional<User> kreaUser = userRequestRepository.findByUserId(id);
            if (kreaUser.isPresent()) {
                log.info("User retrieved successfully for the ID : {} ", id);
                userDetailResponse.setUserId(kreaUser.get().getUserId());
                userDetailResponse.setUser(createUserPublicInfo(kreaUser.get()));
                userDetailResponse.setStatus(ResponseStatus.SUCCESS);
            } else {
                log.error("There is no user found with the ID : {}", id);
                userDetailResponse.setStatus(ResponseStatus.FAILED);
                userDetailResponse.setError(UserStatusErrorResponse.builder()
                        .name(Constants.INVALID_REQUEST)
                        .message(Constants.USER_ID_NOT_FOUND)
                        .timestamp(Timestamp.valueOf(LocalDateTime.now())).build());
            }
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving user for the ID : {} error : {}", id, ex.getMessage(), ex);
            userDetailResponse.setError(UserStatusErrorResponse.builder()
                    .name(Constants.INVALID_REQUEST)
                    .message(Constants.INTERNAL_SERVER_ERROR_MESSAGE)
                    .timestamp(Timestamp.valueOf(LocalDateTime.now())).build());
        }
        return userDetailResponse;
    }

    @Override
    public UserDetailResponse removeUserDetails(Long id) {
        UserDetailResponse userDetailResponse = UserDetailResponse.builder().userId(id).build();
        try {
            log.info("Removing user for the user Id : {}", id);
            apiValidator.validateJobId(id, true);
            Optional<User> kreaUser = userRequestRepository.findByUserId(id);
            if (kreaUser.isPresent()) {
                userRequestRepository.deleteById(id);
                userDetailResponse.setUserId(kreaUser.get().getUserId());
                userDetailResponse.setUser(createUserPublicInfo(kreaUser.get()));
                userDetailResponse.setStatus(ResponseStatus.SUCCESS);
                log.info("User removed successfully for the user Id : {}", id);
            } else {
                log.error("There is no user found with the ID : {}", id);
                userDetailResponse.setStatus(ResponseStatus.FAILED);
                userDetailResponse.setError(UserStatusErrorResponse.builder()
                        .name(Constants.INVALID_REQUEST)
                        .message(Constants.USER_ID_NOT_FOUND)
                        .timestamp(Timestamp.valueOf(LocalDateTime.now())).build());
            }
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving user for the ID : {} error : {}", id, ex.getMessage(), ex);
            userDetailResponse.setError(UserStatusErrorResponse.builder()
                    .name(Constants.INVALID_REQUEST)
                    .message(Constants.INTERNAL_SERVER_ERROR_MESSAGE)
                    .timestamp(Timestamp.valueOf(LocalDateTime.now())).build());
        }
        return userDetailResponse;
    }

    private static UserResponsePublicData createUserPublicInfo(User saveResponse) {
        return UserResponsePublicData.builder()
                .address(saveResponse.getAddress())
                .email(saveResponse.getEmail())
                .name(saveResponse.getName())
                .occupation(saveResponse.getOccupation())
                .role(saveResponse.getRole())
                .userId(saveResponse.getUserId()).build();
    }

}
