package com.dev_curso.libraryapi.controller.dto;

import com.dev_curso.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

/* DTO = Data Transfer Object */
public record AutorDTO(
        @NotBlank(message = "Nome é obrigatório.")
        @Size(max = 100, min = 2, message = "Tamanho do nome é inválido")
        String nome,
        @NotNull(message = "Data de Nascimento é obrigatório.")
        @Past(message = "Data de nascimento deve ser uma data futura.")
        LocalDate dataNascimento,
        @NotBlank(message = "Nacionalidade é obrigatório.")
        @Size(max = 50, min = 3, message = "Tamanho do campo é inválido.")
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
