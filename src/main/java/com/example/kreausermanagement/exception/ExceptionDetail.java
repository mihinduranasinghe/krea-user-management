package com.example.kreausermanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@AllArgsConstructor
@SuperBuilder
@Data
@NoArgsConstructor
public class ExceptionDetail implements Serializable {
    private String field;
    private String value;
}
