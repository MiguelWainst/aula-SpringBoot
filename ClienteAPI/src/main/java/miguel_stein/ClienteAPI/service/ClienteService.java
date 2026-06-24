package miguel_stein.ClienteAPI.service;

import lombok.RequiredArgsConstructor;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import miguel_stein.ClienteAPI.repository.ClienteRepository;
import org.springframework.stereotype.Service;

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
    }
}
