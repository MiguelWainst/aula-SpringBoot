package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.controller.dto.LivroDTO;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.LivroRepository;
import com.dev_curso.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroSerivce {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> acharPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void atualizar(Livro livro) {
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
