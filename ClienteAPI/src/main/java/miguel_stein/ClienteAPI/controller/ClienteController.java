package miguel_stein.ClienteAPI.controller;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.controller.dto.ClienteDTO;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Void> salvarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.mapearParaCliente();
        clienteService.salvar(cliente);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id")
                        .buildAndExpand(cliente.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }
}
