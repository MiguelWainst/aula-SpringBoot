package miguel_stein.ClienteAPI.controller;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.controller.dto.UsuarioDTO;
import miguel_stein.ClienteAPI.mapper.UsuarioMapper;
import miguel_stein.ClienteAPI.service.UsuarioService;
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
        usuarioService.salvar(mapper.toEntity(dto));
    }
}
