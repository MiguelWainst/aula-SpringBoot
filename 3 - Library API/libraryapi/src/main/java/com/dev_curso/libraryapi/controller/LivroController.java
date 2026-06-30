package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.ErroResposta;
import com.dev_curso.libraryapi.controller.dto.LivroDTO;
import com.dev_curso.libraryapi.controller.mappers.LivroMapper;
import com.dev_curso.libraryapi.exceptions.RegistroDuplicadoException;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.service.LivroSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController implements GenericController{

    private final LivroSerivce livroSerivce;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<?> salvarLivro(@RequestBody @Valid LivroDTO livroDTO) {
        try {
            Livro livro = mapper.toEntity(livroDTO);
            livroSerivce.salvar(livro);
            URI location = gerarHeaderLocation(livro.getId());

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            ErroResposta erroResposta = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }
}
