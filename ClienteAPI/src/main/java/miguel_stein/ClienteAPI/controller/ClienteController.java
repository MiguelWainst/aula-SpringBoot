package miguel_stein.ClienteAPI.controller;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.controller.dto.ClienteDTO;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
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

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCliente(
            @RequestBody ClienteDTO clienteDTO,
            @PathVariable("id") String id
    ) {
        Optional<Cliente> clienteOptional = clienteService.acharPorId(UUID.fromString(id));
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        cliente.setNome(clienteDTO.nome());
        cliente.setDataNascimento(clienteDTO.dataNascimento());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf(clienteDTO.cpf());
        clienteService.atualizar(cliente);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> acharClientePorId(@PathVariable("id") String id) {
        Optional<Cliente> clienteOptional = clienteService.acharPorId(UUID.fromString(id));
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDataNascimento(),
                cliente.getEmail(),
                cliente.getCpf()
        );
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> acharPorCpf(@RequestParam String cpf) {
        Optional<Cliente> clienteOptional = clienteService.acharPorCpf(cpf);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDataNascimento(),
                cliente.getEmail(),
                cliente.getCpf()
        );
        return ResponseEntity.ok().body(clienteDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarClientePorId(@PathVariable String id) {
        Optional<Cliente> clienteOptional = clienteService.acharPorId(UUID.fromString(id));
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deletarCliente(clienteOptional.get());
        return ResponseEntity.noContent().build();
    }
}
