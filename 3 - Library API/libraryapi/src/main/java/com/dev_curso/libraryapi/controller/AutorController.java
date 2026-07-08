package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.AutorDTO;
import com.dev_curso.libraryapi.controller.dto.ErroResposta;
import com.dev_curso.libraryapi.controller.mappers.AutorMapper;
import com.dev_curso.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.dev_curso.libraryapi.exceptions.RegistroDuplicadoException;
import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") /* http://host:8080/autores */
@RequiredArgsConstructor
public class AutorController implements GenericController{

    private final AutorService autorService;
    private final AutorMapper mapper;

    /* Camada Rest, Camada View, API */

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autorEntidade = mapper.toEntity(autorDTO);
        autorService.salvar(autorEntidade);

        /* Vai retornar: http://host:8080/autores/{id} */
        /* Exemplo: http://host:8080/autores/a81e6eb2-ffe5-4a58-add9-77e00fc23144 */
        URI location = gerarHeaderLocation(autorEntidade.getId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> autualizarAutorProfessor(
            @PathVariable("id") String id,
            @RequestBody @Valid AutorDTO dto
    ) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autorEntity = autorOptional.get();
        autorEntity.setNome(dto.nome());
        autorEntity.setNacionalidade(dto.nacionalidade());
        autorEntity.setDataNascimento(dto.dataNascimento());
        autorService.atualizar(autorEntity);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterAutor(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
//        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO autorDTO = mapper.toDTO(autor);
                    return ResponseEntity.ok(autorDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());

//        if (autorOptional.isPresent()) {
//            Autor autor = autorOptional.get();
//            AutorDTO dto = mapper.toDTO(autor);
//            return ResponseEntity.ok(dto);
//        }
//        return ResponseEntity.notFound().build();
    }

    /* Usa-se o deleteById quando a tabela não possuir
     * um relacionamento com outras tabelas.
     * Nesse caso, a tabela Autor possui um relacionamento
     * com a tabela de Livro. Um autor possui uma lista de
     * livros. Da para usar o deleteById quando o efeito
     * cascade já foi setado na criação da tabela, daí
     * tudo bem usar.*/
    /** @see AutorService */
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deletarAutorPorId(@PathVariable String id) {
//        var idAutor = UUID.fromString(id);
//        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
//        if (autorOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        autorService.deletarPorId(idAutor);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarAutor(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletarAutor(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    /* Pesquisa por Parametros de um jeito mais simples */
//    @GetMapping
//    public ResponseEntity<List<AutorDTO>> pesquisarAutores(
//            @RequestParam(value = "nome", required = false) String nome,
//            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
//        var listAutores = autorService.pesquisarAutores(nome, nacionalidade);
//        List<AutorDTO> listaAutoresDto = listAutores
//                .stream()
//                .map(mapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(listaAutoresDto);
//    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisa(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nacionalidade,
            @RequestParam(required = false) Integer anoNascimento
    ) {
        var listaPesquisa = autorService.pesquisaParams(nome, nacionalidade, anoNascimento);
        List<AutorDTO> listaDTO = listaPesquisa
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(listaDTO);
    }

}
