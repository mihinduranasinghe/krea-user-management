package com.example.kreausermanagement.service.impl;

import com.example.kreausermanagement.common.Constants;
import com.example.kreausermanagement.common.enums.Error;
import com.example.kreausermanagement.common.enums.ResponseStatus;
import com.example.kreausermanagement.dto.request.LoginRequest;
import com.example.kreausermanagement.dto.request.UserRequest;
import com.example.kreausermanagement.dto.response.AuthenticationResponse;
import com.example.kreausermanagement.dto.response.UserResponsePublicData;
import com.example.kreausermanagement.dto.response.error.ErrorResponse;
import com.example.kreausermanagement.entity.Role;
import com.example.kreausermanagement.entity.Token;
import com.example.kreausermanagement.entity.User;
import com.example.kreausermanagement.repository.IRoleRepository;
import com.example.kreausermanagement.repository.ITokenRepository;
import com.example.kreausermanagement.repository.IUserRepository;
import com.example.kreausermanagement.security.JwtService;
import com.example.kreausermanagement.service.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    @Value("${krea.ums.api.documentation}")
    private String apiDocumentation;

    @Autowired
    private IRoleRepository roleRepository;

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final ITokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(IUserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 ITokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(UserRequest request) {
        try {
            if(repository.findByEmail(request.getEmail()) != null) {
                return AuthenticationResponse.builder()
                        .token(null)
                        .status(ResponseStatus.FAILED)
                        .error(ErrorResponse.builder()
                                .code(Error.USER_ALREADY_EXIST_ERROR.getCode())
                                .message(Error.USER_ID_INVALID_ERROR.getMessage())
                                .informationLink(apiDocumentation)
                                .build())
                        .build();
            }
            Role role = roleRepository.findByName(Constants.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            User user = new User();
            user.setName(request.getUserName());
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setOccupation(request.getOccupation());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(role);
            user = repository.save(user);
            String jwt = jwtService.generateToken(user);
            saveUserToken(jwt, user);
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .data(UserResponsePublicData.builder()
                            .address(user.getAddress())
                            .email(user.getEmail())
                            .name(user.getName())
                            .occupation(user.getOccupation())
                            .role(user.getRole())
                            .userId(user.getUserId()).build())
                    .status(ResponseStatus.SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Exception occurred while registering user for the ID : {} error : {}", request.getEmail(), ex.getMessage(), ex);
            AuthenticationResponse userDetailResponse = AuthenticationResponse.builder()
                    .status(ResponseStatus.FAILED)
                    .error(ErrorResponse.builder()
                            .code(Error.INTERNAL_SERVER_ERROR.getCode())
                            .message(Error.INTERNAL_SERVER_ERROR.getMessage())
                            .informationLink(apiDocumentation)
                            .build())
                    .build();
            return userDetailResponse;
        }

    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = repository.findByEmail(request.getEmail());
            String jwt = jwtService.generateToken(user);
            revokeAllTokenByUser(user);
            saveUserToken(jwt, user);
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .data(UserResponsePublicData.builder()
                            .address(user.getAddress())
                            .email(user.getEmail())
                            .name(user.getName())
                            .occupation(user.getOccupation())
                            .role(user.getRole())
                            .userId(user.getUserId()).build())
                    .status(ResponseStatus.SUCCESS)
                    .build();
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving user for the ID : {} error : {}", request.getEmail(), ex.getMessage(), ex);
            AuthenticationResponse userDetailResponse = AuthenticationResponse.builder()
                    .status(ResponseStatus.FAILED)
                    .error(ErrorResponse.builder()
                            .code(Error.USER_ID_INVALID_ERROR.getCode())
                            .message(ex.getMessage())
                            .informationLink(apiDocumentation)
                            .build())
                    .build();
            return userDetailResponse;
        }
    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getUserId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
