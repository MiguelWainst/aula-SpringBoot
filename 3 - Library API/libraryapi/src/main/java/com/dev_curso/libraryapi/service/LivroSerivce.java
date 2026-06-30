package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.LivroRepository;
import com.dev_curso.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroSerivce {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }
}
