package com.dev_curso.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column
    private String senha;

    @JdbcTypeCode(SqlTypes.ARRAY) // Converte de List para Array.
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
