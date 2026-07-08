package miguel_stein.ClienteAPI.repository;

import miguel_stein.ClienteAPI.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {

    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);

    /* Usando JPQL e fazendo QueryMethods na mão. */
    @Query("select c from Cliente as c where upper(c.nome) like upper(concat('%', ?1, '%')) order by nome")
    List<Cliente> listAllClienteOrderByNomeContaining(String nome);

    @Query("select c from Cliente as c order by c.nome")
    List<Cliente> listAllClienteOrderByName();
}
