package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
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
    * tudo bem usar*/
//    public void deletarPorId(UUID id) {
//        autorRepository.deleteById(id);
//    }

    public void deletarAutor(Autor autor) {
        autorRepository.delete(autor);
    }

}
