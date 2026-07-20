package miguel_stein.ClienteAPI.mapper;

import miguel_stein.ClienteAPI.controller.dto.UsuarioDTO;
import miguel_stein.ClienteAPI.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
