package miguel_stein.ClienteAPI.controller.dto;

import java.util.List;
import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        String login,
        String senha,
        List<String> roles
) {
}
