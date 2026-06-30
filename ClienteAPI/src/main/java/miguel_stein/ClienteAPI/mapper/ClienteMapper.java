package miguel_stein.ClienteAPI.mapper;

import miguel_stein.ClienteAPI.controller.dto.ClienteDTO;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);
    ClienteDTO toDTO(Cliente cliente);
}
