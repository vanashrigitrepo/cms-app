package com.ibm.cms.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

@Column(unique = true, nullable = false)
private String email;

@Column(nullable = false)
    private String password;

    private String role = "USER";

    // Two fields for reset password
    private String resetToken;

    private LocalDateTime tokenExpiry;
}



