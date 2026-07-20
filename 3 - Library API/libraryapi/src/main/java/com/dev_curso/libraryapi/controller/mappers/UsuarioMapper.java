package com.dev_curso.libraryapi.controller.mappers;

import com.dev_curso.libraryapi.controller.dto.UsuarioDTO;
import com.dev_curso.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
