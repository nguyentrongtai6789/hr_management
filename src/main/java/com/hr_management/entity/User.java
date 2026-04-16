package com.hr_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    @NotBlank
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    @NotBlank
    private String password;

    @Column(name = "NHAN_SU_ID", nullable = true)
    private UUID nhanSuId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USERS_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLE")
    private Set<String> roles;

    @Column(name = "IS_ACTIVE", columnDefinition = "NUMBER(1) DEFAULT 1")
    private int isActive;


}
