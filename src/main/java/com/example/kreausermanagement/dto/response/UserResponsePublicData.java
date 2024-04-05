package com.example.kreausermanagement.dto.response;

import com.example.kreausermanagement.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponsePublicData {
    private Long userId;
    private String name;
    private String occupation;
    private String address;
    private String email;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
