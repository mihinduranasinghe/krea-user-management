package com.example.kreausermanagement.exception;

import com.example.kreausermanagement.common.Constants;
import com.example.kreausermanagement.common.enums.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class InternalException extends RuntimeException {
    private final int code;
    private final String message;
    private final String additionalInfo;
    private final ExceptionDetail exceptionDetail;

    public InternalException(Throwable exception, Error error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
        this.additionalInfo = exception.getLocalizedMessage();
        this.exceptionDetail = new ExceptionDetail();
        log.error(Constants.ERROR_LOG_PATTERN, this.getClass().getSimpleName(), error.getCode(), error.getMessage(), additionalInfo, exception);


    }

    public InternalException(Throwable exception, Error error, ExceptionDetail exceptionDetail) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
        this.additionalInfo = exception.getLocalizedMessage();
        this.exceptionDetail = exceptionDetail;
        log.error(Constants.ERROR_LOG_PATTERN, this.getClass().getSimpleName(), error.getCode(), error.getMessage(), additionalInfo, exception);
    }

    public InternalException(Error error, String additionalInfo) {
        super(additionalInfo);
        this.code = error.getCode();
        this.message = error.getMessage();
        this.additionalInfo = additionalInfo;
        this.exceptionDetail = new ExceptionDetail();
        log.error(Constants.ERROR_LOG_PATTERN, this.getClass().getSimpleName(), error.getCode(), error.getMessage(), additionalInfo, new RuntimeException(error.getMessage()));
    }

    public InternalException(Error error, String additionalInfo, ExceptionDetail exceptionDetail) {
        super(additionalInfo);
        this.code = error.getCode();
        this.message = error.getMessage();
        this.additionalInfo = additionalInfo;
        this.exceptionDetail = exceptionDetail;
        log.error(Constants.ERROR_LOG_PATTERN, this.getClass().getSimpleName(), error.getCode(), error.getMessage(), additionalInfo, new RuntimeException(error.getMessage()));
    }

    public InternalException(InternalException InternalException) {
        super(InternalException.getAdditionalInfo());
        this.code = InternalException.getCode();
        this.message = InternalException.getMessage();
        this.additionalInfo = InternalException.getAdditionalInfo();
        this.exceptionDetail = InternalException.getExceptionDetail();
    }
}

