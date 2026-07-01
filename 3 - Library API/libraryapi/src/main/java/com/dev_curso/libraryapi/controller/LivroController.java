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

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController implements GenericController{

    private final LivroSerivce livroSerivce;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvarLivro(@RequestBody @Valid LivroDTO livroDTO) {
        Livro livro = mapper.toEntity(livroDTO);
        livroSerivce.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obterLivroPorId(@PathVariable String id) {
        Optional<Livro> livroOptional = livroSerivce.acharPorId(UUID.fromString(id));
        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LivroDTO livroDTO = mapper.toDTO(livroOptional.get());
        return ResponseEntity.ok().body(livroDTO);
    }
}
