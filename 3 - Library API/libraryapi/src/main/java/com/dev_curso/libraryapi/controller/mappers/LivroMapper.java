package com.dev_curso.libraryapi.controller.mappers;

import com.dev_curso.libraryapi.controller.dto.CadastroLivroDTO;
import com.dev_curso.libraryapi.controller.dto.PesquisaLivroDTO;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);

    public abstract PesquisaLivroDTO toDTO(Livro livro);
}
