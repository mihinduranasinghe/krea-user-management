package com.example.kreausermanagement.controller;

import com.example.kreausermanagement.dto.request.LoginRequest;
import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.AuthenticationResponse;
import com.example.kreausermanagement.dto.response.UserUpdateResponse;
import com.example.kreausermanagement.dto.response.error.ErrorResponse;
import com.example.kreausermanagement.service.IAuthenticationService;
import com.example.kreausermanagement.service.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://main.d3j8hln1tqdtxw.amplifyapp.com/"
})
@RequestMapping(value = "/v1/krea/users")
public class AuthenticationController {

    private final IAuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @Operation(summary = "User Register", description = "This endpoint will be used to register user to the system")
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
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRequest request
    ) {
        AuthenticationResponse authenticationResponse = authService.register(request);
        return new ResponseEntity<>(authenticationResponse, getHttpStatus(authenticationResponse));
    }

    @Operation(summary = "User Login", description = "This endpoint will be used to login user to the system")
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
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse authenticationResponse = authService.authenticate(request);
        return new ResponseEntity<>(authenticationResponse, getHttpStatus(authenticationResponse));
    }

    private HttpStatus getHttpStatus(AuthenticationResponse authenticationResponse) {
        return switch (authenticationResponse.getStatus()) {
            case SUCCESS -> HttpStatus.OK;
            case FAILED -> HttpStatus.BAD_REQUEST;
        };
    }
}
