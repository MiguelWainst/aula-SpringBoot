package com.dev_curso.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN(message = "O campo deve conter um ISBN válido.")
        @NotBlank(message = "Campo obrigatório.")
        String isbn,
        @NotBlank(message = "Campo obrigatório.")
        String titulo,
        @Past(message = "A data deve ser uma data passada.")
        @NotNull(message = "Campo obrigatório.")
        LocalDate dataPublicacao,
        @NotBlank(message = "O gênero do livro é obrigatório")
        String genero,
        BigDecimal preco,
        UUID idAutor
) {
}
