package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.CadastroLivroDTO;
import com.dev_curso.libraryapi.controller.dto.PesquisaLivroDTO;
import com.dev_curso.libraryapi.controller.mappers.LivroMapper;
import com.dev_curso.libraryapi.model.GeneroLivro;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.service.LivroSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<PesquisaLivroDTO>> pesquisarLivrosParam(
            @RequestParam(required = false, value = "isbn") String isbn,
            @RequestParam(required = false, value = "titulo") String titulo,
            @RequestParam(required = false, value = "nomeAutor") String nomeAutor,
            @RequestParam(required = false, value = "genero") GeneroLivro genero,
            @RequestParam(required = false, value = "anoPublicacao") Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ) {
        Page<Livro> livrosPage = livroSerivce.pesquisarParam(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<PesquisaLivroDTO> resultado = livrosPage.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarLivro(
            @RequestBody @Valid CadastroLivroDTO livroDTO,
            @PathVariable String id
    ) {
        return livroSerivce.acharPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entityAux = mapper.toEntity(livroDTO);
                    livro.setIsbn(entityAux.getIsbn());
                    livro.setDataPublicacao(entityAux.getDataPublicacao());
                    livro.setPreco(entityAux.getPreco());
                    livro.setTitulo(entityAux.getTitulo());
                    livro.setGenero(entityAux.getGenero());
                    livro.setAutor(entityAux.getAutor());
                    livroSerivce.atualizar(livro);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable String id) {
        return livroSerivce.acharPorId(UUID.fromString(id))
                .map(livro -> {
                    livroSerivce.deletarLivro(livro);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}



