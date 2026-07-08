package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.repository.AutorRepository;
import com.dev_curso.libraryapi.repository.LivroRepository;
import com.dev_curso.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev_curso.libraryapi.repository.Specifications.AutorSpecs.*;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já exista");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    /* Usa-se o deleteById quando a tabela não possuir
    * um relacionamento com outras tabelas.
    * Nesse caso, a tabela Autor possui um relacionamento
    * com a tabela de Livro. Um autor possui uma lista de
    * livros. Da para usar o deleteById quando o efeito
    * cascade já foi setado na criação da tabela, daí
    * tudo bem usar. */
    /** @see com.dev_curso.libraryapi.controller.AutorController */
//    public void deletarPorId(UUID id) {
//        autorRepository.deleteById(id);
//    }

    public void deletarAutor(Autor autor) {
        if (temLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Erro na exclusão: registro está sendo utilizado."
            );
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisarAutores(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeContainingAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return autorRepository.findByNomeContaining(nome);
        }
        if (nacionalidade != null) {
            return  autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }

    public List<Autor> pesquisaParams(String nome, String nacionalidade, Integer anoNasicmento) {
        Specification<Autor> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (nome != null) {
            specs = specs.and(nomeLike(nome));
        }
        if (nacionalidade != null) {
            specs = specs.and(nacionalidadeEqual(nacionalidade));
        }
        if (anoNasicmento != null) {
            specs = specs.and(anoEqual(anoNasicmento));
        }

        return autorRepository.findAll(specs);
    }

    private boolean temLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
