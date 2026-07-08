package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.LivroRepository;
import com.dev_curso.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev_curso.libraryapi.repository.Specifications.LivroSpecs.*;

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

    public List<Livro> pesquisarParam(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {

        // import static com.dev_curso.libraryapi.repository.Specifications.LivroSpecs.*;
        /* select * from livro where 0 = 0 */
        Specification<Livro> specs = Specification.where( (root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }
        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }
        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }
        return livroRepository.findAll(specs);
    }

    public void deletarLivro(Livro livro) {
        livroRepository.delete(livro);
    }
}
