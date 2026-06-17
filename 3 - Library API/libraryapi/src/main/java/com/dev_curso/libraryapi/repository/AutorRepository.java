package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
