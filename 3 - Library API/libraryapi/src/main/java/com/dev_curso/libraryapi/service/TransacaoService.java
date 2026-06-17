package com.dev_curso.libraryapi.service;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.AutorRepository;
import com.dev_curso.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar() {
        // Salva o autor
        Autor autor = new Autor();
        autor.setNome("Rafael");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(2001, 8, 4));

        autorRepository.save(autor);

        // Salva um livro
        Livro livro = new Livro();
        livro.setIsbn("103453-83080");
        livro.setPreco(BigDecimal.valueOf(99.90));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Casa Mal Assombrada");
        livro.setDataPublicacao(LocalDate.of(1998, 10, 31));
        livro.setAutor(autor);

        // Salva um livro
        Livro livro2 = new Livro();
        livro2.setIsbn("03213-30523");
        livro2.setPreco(BigDecimal.valueOf(10.99));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Casa Mal Assombrada 2");
        livro2.setDataPublicacao(LocalDate.of(1999, 2, 28));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        livroRepository.saveAll(autor.getLivros());

        if(!autor.getNome().equals("Rafael")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
