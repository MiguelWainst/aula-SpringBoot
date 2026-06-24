package miguel_stein.ClienteAPI.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private LocalDate dataNascimento;

    private String email;

    private String cpf;

    @CreatedDate
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Deprecated
    public Cliente() {
    }
}
