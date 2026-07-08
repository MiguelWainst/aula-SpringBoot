package com.dev_curso.libraryapi.repository.Specifications;

import com.dev_curso.libraryapi.model.Autor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AutorSpecs {

    public static Specification<Autor> nomeLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Autor> nacionalidadeEqual(String nacionalidade) {
        return (root, query, cb) -> cb.equal(cb.upper(root.get("nacionalidade")), nacionalidade.toUpperCase());
    }

    public static Specification<Autor> anoEqual(Integer anoNascimento) {
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("dataNascimento"),
                        cb.literal("YYYY")), anoNascimento.toString());
    }
}
