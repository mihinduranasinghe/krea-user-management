package com.example.kreausermanagement.controller;

import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.*;
import com.example.kreausermanagement.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://main.d3j8hln1tqdtxw.amplifyapp.com/"
})
@RequestMapping(value = "/v1/krea")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get All User Details", description = "This endpoint will be used to retrieve all user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "207", description = "Partially Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Parameter Request",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/users")
    public ResponseEntity<UsersResponse> retrieveAllUsers() {
        UsersResponse jobStatusResponse = userService.getUsers();
        return new ResponseEntity<>(jobStatusResponse, getHttpStatus(jobStatusResponse));
    }

    private HttpStatus getHttpStatus(UsersResponse jobRequestResponse) {
        return switch (jobRequestResponse.getStatus()) {
            case SUCCESS -> HttpStatus.OK;
            case FAILED -> HttpStatus.BAD_REQUEST;
        };
    }

    @Operation(summary = "submit user information", description = "This endpoint will be used to create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response",
                    content = @Content(schema = @Schema(implementation = UserRequestResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "207", description = "Partially Success Response",
                    content = @Content(schema = @Schema(implementation = UserRequestResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Parameter Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/users")
    public ResponseEntity<UserRequestResponse> submitNewUser(@RequestBody UserRequest request) {
        UserRequestResponse jobRequestResponse = userService.addUserDetails(request);
        return new ResponseEntity<>(jobRequestResponse, getHttpStatus(jobRequestResponse));
    }
    private HttpStatus getHttpStatus(UserRequestResponse jobRequestResponse) {
        return switch (jobRequestResponse.getData().getStatus()) {
            case SUCCESS -> HttpStatus.OK;
            case FAILED -> HttpStatus.BAD_REQUEST;
        };
    }

    @Operation(summary = "Update user information", description = "This endpoint will be used to update the user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response",
                    content = @Content(schema = @Schema(implementation = UserUpdateResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "207", description = "Partially Success Response",
                    content = @Content(schema = @Schema(implementation = UserUpdateResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Parameter Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<UserUpdateResponse> updateExistingUser(@PathVariable Long id, @RequestBody UserRequest request) {
        UserUpdateResponse jobRequestResponse = userService.updateUserDetails(id, request);
        return new ResponseEntity<>(jobRequestResponse, getHttpStatus(jobRequestResponse));
    }
    private HttpStatus getHttpStatus(UserUpdateResponse jobRequestResponse) {
        return switch (jobRequestResponse.getData().getStatus()) {
            case SUCCESS -> HttpStatus.OK;
            case FAILED -> HttpStatus.BAD_REQUEST;
        };
    }

    @Operation(summary = "Delete User Details", description = "This endpoint will be used to delete user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "207", description = "Partially Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Parameter Request",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailResponse> retrieveUser(@PathVariable Long id) {
        UserDetailResponse jobStatusResponse = userService.getUserDetails(id);
        return new ResponseEntity<>(jobStatusResponse, getHttpStatus(jobStatusResponse));
    }

    private HttpStatus getHttpStatus(UserDetailResponse jobSubmissionDetail) {
        return switch (jobSubmissionDetail.getStatus()) {
            case SUCCESS -> HttpStatus.OK;
            case FAILED -> HttpStatus.BAD_REQUEST;
        };
    }

    @Operation(summary = "Get User Details", description = "This endpoint will be used to retrieve user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "207", description = "Partially Success Response",
                    content = @Content(schema = @Schema(implementation = UserDetailResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal System Error",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Parameter Request",
                    content = @Content(schema = @Schema(implementation = UserStatusErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailResponse> deleteUser(@PathVariable Long id) {
        UserDetailResponse jobStatusResponse = userService.removeUserDetails(id);
        return new ResponseEntity<>(jobStatusResponse, getHttpStatus(jobStatusResponse));
    }

}
