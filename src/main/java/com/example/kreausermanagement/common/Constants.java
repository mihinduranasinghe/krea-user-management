package com.example.kreausermanagement.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    // environment
    public static final String APPLICATION_ENVIRONMENT = "APPLICATION_ENV";

    // Common messages
    public static final int USER_ID_INVALID_ERROR_CODE = 100;
    public static final String USER_ID_INVALID_MESSAGE = "user_id is invalid";
    public static final int INTERNAL_SERVER_ERROR_CODE = 900;
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Unexpected error while processing the message";

    // Error Messages
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String ORDER_DATE = "order_date";
    public static final String USER_ID_NOT_FOUND = "User Id Not Found";
    public static final String ERROR_LOG_PATTERN = "### [{}]  occurred  with code [{}] : message [{}] additionalInfo : [{}]";

    // User roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

}