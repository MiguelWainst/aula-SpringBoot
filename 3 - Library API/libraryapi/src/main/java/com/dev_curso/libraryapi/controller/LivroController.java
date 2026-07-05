package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.CadastroLivroDTO;
import com.dev_curso.libraryapi.controller.dto.PesquisaLivroDTO;
import com.dev_curso.libraryapi.controller.mappers.LivroMapper;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.service.LivroSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController implements GenericController{

    private final LivroSerivce livroSerivce;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvarLivro(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {
        Livro livro = mapper.toEntity(cadastroLivroDTO);
        livroSerivce.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obterLivroPorId(@PathVariable String id) {
        return livroSerivce.acharPorId(UUID.fromString(id))
                .map(livro -> {
                    var pesquisaLivroDTO = mapper.toDTO(livro);
                    return ResponseEntity.ok(pesquisaLivroDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PesquisaLivroDTO>> listarTodosLivros() {
        Optional<List<Livro>> livrosOptional = livroSerivce.listarTodosLivros();
        if (livrosOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<PesquisaLivroDTO> cadastroLivroDTOS = livrosOptional
                .get()
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(cadastroLivroDTOS);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable String id) {
        if (temLivroId(id)) {
            livroSerivce.deletarLivro(UUID.fromString(id));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean temLivroId(String id) {
        return livroSerivce.acharPorId(UUID.fromString(id)).isPresent();
    }
}



