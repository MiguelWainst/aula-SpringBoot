package com.dev_curso.libraryapi.controller.mappers;

import com.dev_curso.libraryapi.controller.dto.AutorDTO;
import com.dev_curso.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
