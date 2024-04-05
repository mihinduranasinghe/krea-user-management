package com.example.kreausermanagement.exception;

import com.example.kreausermanagement.common.enums.Error;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@EqualsAndHashCode(callSuper = false)
public class RestException extends InternalException {
    private final HttpStatus httpStatus;

    public RestException(HttpStatus httpStatus, Throwable exception, Error error) {
        super(exception, error);
        this.httpStatus = httpStatus;
    }

    public RestException(HttpStatus httpStatus, Throwable exception, Error error, ExceptionDetail exceptionDetail) {
        super(exception, error, exceptionDetail);
        this.httpStatus = httpStatus;

    }

    public RestException(HttpStatus httpStatus, Error error, String additionalInfo) {
        super(error, additionalInfo);
        this.httpStatus = httpStatus;
    }

    public RestException(HttpStatus httpStatus, Error error, String additionalInfo, ExceptionDetail exceptionDetail) {
        super(error, additionalInfo, exceptionDetail);
        this.httpStatus = httpStatus;
    }

    public RestException(HttpStatus httpStatus, InternalException scsIx) {
        super(scsIx);
        this.httpStatus = httpStatus;
    }
}
