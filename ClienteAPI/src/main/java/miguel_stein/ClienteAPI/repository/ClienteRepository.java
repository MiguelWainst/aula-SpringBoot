package miguel_stein.ClienteAPI.repository;

import miguel_stein.ClienteAPI.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(String cpf);

    @Query("select c from Cliente as c order by c.nome")
    List<Cliente> listAllClientesOrderByName();

}
