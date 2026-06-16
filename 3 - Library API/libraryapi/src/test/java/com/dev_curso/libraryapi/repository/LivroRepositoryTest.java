package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("10013-83080");
        livro.setPreco(BigDecimal.valueOf(10.99));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Elon Musk");
        livro.setDataPublicacao(LocalDate.of(2023, 11, 29));

        Autor autor = autorRepository.findById(UUID.fromString("a81e6eb2-ffe5-4a58-add9-77e00fc23144")).orElse(null);
        livro.setAutor(autor);


        var livroSalvo = livroRepository.save(livro);
        System.out.println("Livro adicionado: " + livroSalvo);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("10013-83080");
        livro.setPreco(BigDecimal.valueOf(10.99));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Elon Musk");
        livro.setDataPublicacao(LocalDate.of(2023, 11, 29));

        Autor autor = new Autor();
        autor.setNome("Dostoievski");
        autor.setNacionalidade("Francesa");
        autor.setDataNascimento(LocalDate.of(1823, 3, 19));

        livro.setAutor(autor);


        var livroSalvo = livroRepository.save(livro);
        System.out.println("Livro adicionado: " + livroSalvo);
    }

    @Test
    void atualizarTest() {
        var id = UUID.fromString("6e93bab4-f328-4315-97a9-58debb394fc4");
        Optional<Livro> possivelLivro = livroRepository.findById(id);

        if(possivelLivro.isPresent()) {
            Livro livroEncontrado = possivelLivro.get();
            System.out.println("Dados do livro:");
            System.out.println(livroEncontrado);

            livroEncontrado.setTitulo("Memorias do Subsolo");
            livroEncontrado.setPreco(BigDecimal.valueOf(58.99));
            livroEncontrado.setGenero(GeneroLivro.MISTERIO);
            System.out.println("-------------------------------------");

            System.out.println("Dados do livro atualizado:");
            System.out.println(livroEncontrado);

            livroRepository.save(livroEncontrado);
        }
    }

    @Test
    void listarTest() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    @Test
    void countTest() {
        System.out.println("Quantidade de livros: " + livroRepository.count());
    }

    @Test
    void deleteByIdTest() {
        var id = UUID.fromString("6706b46e-e910-490d-b725-403f1ead1120");
        livroRepository.deleteById(id);
        System.out.println("Deletado!");
    }

    @Test
    void deleteByObjectTest() {
        var id = UUID.fromString("1a2d1dbe-033d-43ee-b9a6-c53045377f96");
        var possivelLivro = livroRepository.findById(id);

        if(possivelLivro.isPresent()) {
            var livro = possivelLivro.get();
            livroRepository.delete(livro);
            System.out.println("Deletado!");
        }
    }
}