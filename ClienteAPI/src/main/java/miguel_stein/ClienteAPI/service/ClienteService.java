package miguel_stein.ClienteAPI.service;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.exception.OperacaoNaoPermitida;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import miguel_stein.ClienteAPI.validators.ClienteValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static miguel_stein.ClienteAPI.repository.specifications.ClienteSpecs.*;

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

    public Page<Cliente> listarPorParams(String nome, Integer anoNascimento, Integer pagina, Integer tamanhoPagina) {

        Specification<Cliente> spec = Specification.where((root, query, cb) -> cb.conjunction());

        if (nome != null) {
            spec = spec.and(nomeLike(nome));
        }
        if (anoNascimento != null) {
            spec = spec.and(anoEqual(anoNascimento));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return clienteRepository.findAll(spec, pageRequest);
    }

    public boolean existePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent();
    }
}
