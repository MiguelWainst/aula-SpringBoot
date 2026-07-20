package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.UsuarioDTO;
import com.dev_curso.libraryapi.controller.mappers.UsuarioMapper;
import com.dev_curso.libraryapi.model.Usuario;
import com.dev_curso.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        usuarioService.salvar(entity);
    }
}
