package com.dev_curso.libraryapi.validator;

import com.dev_curso.libraryapi.exceptions.RegistroDuplicadoException;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if (existeIsbnLivro(livro)) {
            throw new RegistroDuplicadoException("Este ISBN já está sendo usado!");
        }
    }

    private boolean existeIsbnLivro(Livro livro) {
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) {
            return livroOptional.isPresent();
        }

        return livroOptional.isPresent() && !livroOptional.get().getIsbn().equals(livro.getIsbn());
    }
}
