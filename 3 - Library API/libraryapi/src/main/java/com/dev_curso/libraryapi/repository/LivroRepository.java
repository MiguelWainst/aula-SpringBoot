package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query method: cirado em tempo de execução.
    /*
    É possível criar os meus próprios métodos dentro da interface.
    O próprio Framework entende o que eu quero dizer com finByCriterio.
    Tem como fazer uma busca usando mais de 1 critério, é só continuar adicionando "And".
     */

    // select * from livro where id_autor = ?
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = ?
    List<Livro> findByTituloContaining(String titulo);

    // select * from livro where isbn = ?
    List<Livro> findByIsbn(String isbn);
//    Optional<Livro> findByIsbn(String isbn); // Retorna um optional

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or preco = ?
    List<Livro> findByTituloOrPreco(String titulo, BigDecimal preco);

    // JPQL
    @Query("select l from Livro as l order by l.titulo, l.preco") // Nome da propriedade no Java e nao no BD.
    List<Livro> listAllLivroOrderByName();

    // SQL: select a.* from livro l join autor a on a.id = l.id_autor;
    @Query("select a from Livro l join l.autor a")
    List<Autor> listAutorOfLivros();

    // SQL: select distinct l.titulo from livro l;
    @Query("select distinct l.titulo from Livro l")
    List<String> listNameOfLivro();

    /*
    SQL:
    select l.genero
    from livro l
    join autor a on a.id = l.id_autor
    where a.nacionalidade = 'Brasileira'
    order by l.genero;
     */
    @Query("""
        select l.genero 
        from Livro l 
        join l.autor a 
        where a.nacionalidade = 'Brasileira' 
        order by l.genero
    """)
    List<GeneroLivro> findGeneroByNacionalidade();

    // Named parameters -> Parametros nomeados, ou seja, por nome (:parametro)
    @Query("select l from Livro l where genero = :genero order by :oderBy")
    List<Livro> findByGeneroNamed(
            @Param("genero") GeneroLivro generoLivro,
            @Param("oderBy") String orderBy
    );

    // Positional Parameters -> por posição, igual as auldas de JDBC e DAO
    @Query("select l from Livro l where genero = ?1 order by ?2")
    List<Livro> findByGeneroPositional(GeneroLivro generoLivro, String orderBy);
}
