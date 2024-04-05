package com.example.kreausermanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "krea_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "krea_user_id")
    private Long userId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
