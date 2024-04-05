package com.example.kreausermanagement.utils;

import com.example.kreausermanagement.common.enums.Error;
import com.example.kreausermanagement.exception.ExceptionDetail;
import com.example.kreausermanagement.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiValidator {
    public void validateJobId(Long jobId, boolean isRequired) {
        if (isRequired && jobId.describeConstable().isEmpty()) {
            ExceptionDetail exceptionDetail = ExceptionDetail.builder().field("data").build();
            throw new RestException(HttpStatus.BAD_REQUEST, Error.USER_ID_INVALID_ERROR, "User ID is empty", exceptionDetail);
        }
    }
}
