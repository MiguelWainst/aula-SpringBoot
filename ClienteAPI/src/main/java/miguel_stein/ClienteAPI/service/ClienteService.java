package miguel_stein.ClienteAPI.service;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.exception.OperacaoNaoPermitida;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import miguel_stein.ClienteAPI.validators.ClienteValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidator clienteValidator;

    public Cliente salvar(Cliente cliente) {
        clienteValidator.validar(cliente);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Cliente cliente) {
        clienteValidator.validar(cliente);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> acharPorId(UUID id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> acharPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> acharTodos() {
        return clienteRepository.listAllClienteOrderByName();
    }

    public List<Cliente> listarClientesPorNome(String nome) {
        return clienteRepository.listAllClienteOrderByNomeContaining(nome);
    }

    public void deletarCliente(Cliente cliente) {
        if (existePorCpf(cliente.getCpf())) {
            clienteRepository.delete(cliente);
            return;
        }
        throw new OperacaoNaoPermitida("Impossível deletar um cliente que não existe (CPF nulo)");
    }

    public boolean existePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent();
    }
}
