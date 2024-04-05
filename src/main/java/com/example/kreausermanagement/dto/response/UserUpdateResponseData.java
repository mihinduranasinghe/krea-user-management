package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.common.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseData {
    private ResponseStatus status;
}
