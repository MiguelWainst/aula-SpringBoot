package miguel_stein.ClienteAPI.repository.specifications;

import miguel_stein.ClienteAPI.model.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecs {

    public static Specification<Cliente> nomeLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Cliente> anoEqual(Integer anoNascimento) {
        // to_char(data_publicacao, 'YYYY') = anoPublicacao
        return (root, query, cb) -> cb.equal(cb.function("to_char", String.class, root.get("dataNascimento"), cb.literal("YYYY")), anoNascimento.toString());
    }

}
