package com.dev_curso.libraryapi.repository;


import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    // Salva um autor no bd
    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Ronaldo");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1931, 2, 16));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Ronaldo");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1931, 2, 16));
        repository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("103453-83080");
        livro.setPreco(BigDecimal.valueOf(99.90));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Casa Mal Assombrada");
        livro.setDataPublicacao(LocalDate.of(1998, 10, 31));
        livro.setAutor(autor);

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
    }

    // Atualiza um autor
    @Test
    public void atualizarTest() {
        var id = UUID.fromString("6c1943be-3e1c-4ccd-a881-18ac51cf392e");
        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setNacionalidade("Israelense");

            repository.save(autorEncontrado);
        }

    }

    // Lista todos os autores
    @Test
    public void listarTest() {
        List<Autor> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void listarLivrosAutor() {
        var id = UUID.fromString("6c1943be-3e1c-4ccd-a881-18ac51cf392e");
        var autor = repository.findById(id).get();
        List<Livro> listLivro = livroRepository.findByAutor(autor);
        autor.setLivros(listLivro);

        autor.getLivros().forEach(System.out::println);
    }

    // Conta quantos autores tem
    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    // Deleta um autor por id
    @Test
    public void deletePorIdTeste() {
        var id = UUID.fromString("539f1903-770e-4b05-b161-22ad10ea587e");
        repository.deleteById(id);
    }

    // Delata por objeto
    @Test
    public void deletePorObjectTeste() {
        var id = UUID.fromString("539f1903-770e-4b05-b161-22ad10ea587e");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
}
