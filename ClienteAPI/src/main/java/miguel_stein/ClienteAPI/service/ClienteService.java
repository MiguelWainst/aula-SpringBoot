package miguel_stein.ClienteAPI.service;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public void salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            clienteRepository.save(cliente);
        }
        throw new IllegalArgumentException("Este cliente já está cadastrado!");
    }

    public void atualizar(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("Impossível atualizar um cliente inexistente.");
        }
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> acharPorId(UUID id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> acharPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> acharTodos() {
        return clienteRepository.listAllClientesOrderByName();
    }

    public void deletarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
}
