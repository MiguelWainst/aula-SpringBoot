package com.dev_curso.libraryapi.controller.mappers;

import com.dev_curso.libraryapi.controller.dto.LivroDTO;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(livroDTO.idAutor()).orElse(null))")
    public abstract Livro toEntity(LivroDTO livroDTO);

    public abstract LivroDTO toDTO(Livro livro);
}
