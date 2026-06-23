package com.dev_curso.libraryapi.controller.dto;

import com.dev_curso.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/* DTO = Data Transfer Object */
public record AutorDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,
        @NotNull(message = "Data de Nascimento é obrigatório.")
        LocalDate dataNascimento,
        @NotBlank(message = "Nacionalidade é obrigatório.")
        String nacionalidade, UUID id
) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
