package com.dev_curso.libraryapi.controller;

import com.dev_curso.libraryapi.controller.dto.AutorDTO;
import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.service.AutorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") /* http://host:8080/autores */
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService service) {
        this.autorService = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        Autor autorEntidade = autor.mapearParaAutor();
        autorService.salvar(autorEntidade);

        /* Vai retornar: http://host:8080/autores/{id} */
        /* Exemplo: http://host:8080/autores/a81e6eb2-ffe5-4a58-add9-77e00fc23144 */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterAutor(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade(), autor.getId());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

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
    public ResponseEntity<Void> deletarAutor(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletarAutor(autorOptional.get());
        return ResponseEntity.noContent().build();
    }
}
