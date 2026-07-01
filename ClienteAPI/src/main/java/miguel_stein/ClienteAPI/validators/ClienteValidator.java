package miguel_stein.ClienteAPI.validators;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.exception.RegistroDuplicadoException;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private final ClienteRepository clienteRepository;

    public void validar(Cliente cliente) {
        if (existeCpfCadastro(cliente)) {
            throw new RegistroDuplicadoException("Erro: Um cliente já existe com esse CPF!");
        }
        if (existeEmailCadastro(cliente)) {
            throw new RegistroDuplicadoException("Erro: Email em uso.");
        }
    }

    private boolean existeCpfCadastro(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cliente.getCpf());
        boolean present = clienteOptional.isPresent();
        if (cliente.getId() == null) {
            return present;
        }
        return present && !cliente.getId().equals(clienteOptional.get().getId());
    }

    private boolean existeEmailCadastro(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(cliente.getEmail());
        boolean present = clienteOptional.isPresent();
        if (cliente.getId() == null) {
            return present;
        }
        return present && !cliente.getId().equals(clienteOptional.get().getId());
    }

}
