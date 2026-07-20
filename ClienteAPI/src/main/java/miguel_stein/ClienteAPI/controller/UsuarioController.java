package miguel_stein.ClienteAPI.controller;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.controller.dto.UsuarioDTO;
import miguel_stein.ClienteAPI.mapper.UsuarioMapper;
import miguel_stein.ClienteAPI.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    public void salvar(@RequestBody UsuarioDTO dto) {
        usuarioService.salvar(mapper.toEntity(dto));
    }
}
