package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNomeContaining(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeContainingAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome, LocalDate dataNascimento, String Nacionalidade
    );
}
