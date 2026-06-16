package com.dev_curso.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
public class Livro {

    // id uuid not null primary key
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    // isbn varchar(20) not null
    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    // titulo varchar(120) not null
    @Column(name = "titulo", length = 120, nullable = false)
    private String titulo;

    // data_publicacao date not null
    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    // genero varchar(30) not null
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    // preco numeric(18,2)
    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    // id_autor uuid not null references autor(id),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
