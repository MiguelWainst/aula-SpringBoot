package miguel_stein.ClienteAPI.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import miguel_stein.ClienteAPI.model.entity.Cliente;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteDTO(
        UUID id,
        @NotBlank(message = "Nome não pode ser nulo.")
        String nome,
        @Past(message = "A data deve ser uma data passada.")
        @NotNull(message = "Data de nascimento é obrigatório.")
        LocalDate dataNascimento,
        @Email(message = "Formato de E-mail inválido.")
        @NotBlank(message = "E-mail é obrigatório.")
        String email,
        @NotBlank(message = "CPF é obrigatório.")
        String cpf
) {

    public Cliente mapearParaCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setDataNascimento(this.dataNascimento);
        cliente.setEmail(this.email);
        cliente.setCpf(this.cpf);
        return cliente;
    }
}
