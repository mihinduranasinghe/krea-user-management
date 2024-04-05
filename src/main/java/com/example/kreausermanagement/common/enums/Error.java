package com.example.kreausermanagement.common.enums;

import com.example.kreausermanagement.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {
    USER_ID_INVALID_ERROR(Constants.USER_ID_INVALID_ERROR_CODE, Constants.USER_ID_INVALID_MESSAGE),
    INTERNAL_SERVER_ERROR(Constants.INTERNAL_SERVER_ERROR_CODE, Constants.INTERNAL_SERVER_ERROR_MESSAGE);

    private final int code;
    private final String message;
}
