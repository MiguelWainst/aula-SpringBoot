package miguel_stein.ClienteAPI.controller.dto;

import miguel_stein.ClienteAPI.model.entity.Cliente;

import java.time.LocalDate;

public record ClienteDTO(String nome, LocalDate dataNascimento, String email, String cpf) {

    public Cliente mapearParaCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setDataNascimento(this.dataNascimento);
        cliente.setEmail(this.email);
        cliente.setCpf(this.cpf);
        return cliente;
    }
}
