package com.dev_curso.libraryapi.controller.mappers;

import com.dev_curso.libraryapi.controller.dto.AutorDTO;
import com.dev_curso.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
