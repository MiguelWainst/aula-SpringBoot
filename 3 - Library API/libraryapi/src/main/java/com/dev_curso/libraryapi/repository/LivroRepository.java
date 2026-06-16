package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query method
    /*
    É possível criar os meus próprios métodos dentro da interface.
    O próprio Framework entende o que eu quero dizer com finByCriterio.
    Tem como fazer uma busca usando mais de 1 critério, é só continuar adicionando "And".
     */

    // select * from livro where id_autor = ?
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = ?
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = ?
    List<Livro> findByIsbn(String isbn);
    Optional<Livro> findByIsbnOptional(String isbn); // Retorna um optional

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or preco = ?
    List<Livro> findByTituloOrPreco(String titulo, BigDecimal preco);
}
