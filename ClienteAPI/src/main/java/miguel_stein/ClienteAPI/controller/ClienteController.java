package miguel_stein.ClienteAPI.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.controller.dto.ClienteDTO;
import miguel_stein.ClienteAPI.controller.dto.ErroResposta;
import miguel_stein.ClienteAPI.exception.RegistroDuplicadoException;
import miguel_stein.ClienteAPI.mapper.ClienteMapper;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper mapper;

    @PostMapping
    public ResponseEntity<?> salvarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.mapearParaCliente();
        clienteService.salvar(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarCliente(
            @RequestBody @Valid ClienteDTO clienteDTO,
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
        ClienteDTO clienteDTO = mapper.toDTO(cliente);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping("cpf")
    public ResponseEntity<ClienteDTO> acharPorCpf(@RequestParam String cpf) {
        Optional<Cliente> clienteOptional = clienteService.acharPorCpf(cpf);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        ClienteDTO clienteDTO = mapper.toDTO(cliente);
        return ResponseEntity.ok().body(clienteDTO);
    }

//    @GetMapping
//    public ResponseEntity<List<ClienteDTO>> acharTodos() {
//        List<Cliente> listCliente= clienteService.acharTodos();
//        List<ClienteDTO> clienteDTOS = listCliente
//                .stream()
//                .map(mapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(clienteDTOS);
//    }

//    @GetMapping("nomes")
//    public ResponseEntity<List<ClienteDTO>> listarPorNome(@RequestParam String nome) {
//        List<Cliente> clientes = clienteService.listarClientesPorNome(nome);
//        List<ClienteDTO> clientesDTOS = clientes.
//                stream().
//                map(mapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(clientesDTOS);
//    }

    @GetMapping("pesquisa")
    public ResponseEntity<List<ClienteDTO>> pesquisaPorParam(
            @RequestParam(required = false, value = "nome") String nome,
            @RequestParam(required = false, value = "anoNascimento") Integer anoNascimento
    ) {
        var resultado = clienteService
                .listarPorParams(nome, anoNascimento)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(resultado);
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
