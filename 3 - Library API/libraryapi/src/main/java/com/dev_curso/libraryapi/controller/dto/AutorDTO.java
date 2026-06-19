package com.dev_curso.libraryapi.controller.dto;

import com.dev_curso.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

/* DTO = Data Transfer Object */
public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade, UUID id) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
