package miguel_stein.ClienteAPI.validators;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.exception.RegistroDuplicadoException;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private final ClienteRepository clienteRepository;

    public void validar(Cliente cliente) {
        if (existeClienteCadastro(cliente)) {
            throw new RegistroDuplicadoException("Erro: Este cliente já existe!");
        }
    }

    private boolean existeClienteCadastro(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cliente.getId());
        boolean present = clienteOptional.isPresent();
        if (cliente.getId() == null) {
            return present;
        }
        return present && !cliente.getId().equals(clienteOptional.get().getId());
    }
}
