package com.dev_curso.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
public class Autor {

    // id uuid not null primary key
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // nome varchar(100) not null
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    // data_nascimento date not null,
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // nacionalidade varchar(50) not null
    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;

    @Deprecated
    public Autor(){

    } // Para o uso do Framework
}
