package com.teste_aprendizagem.spring_frete.dto;

import com.teste_aprendizagem.spring_frete.model.entity.Pedido;

import java.util.UUID;

public record PedidoDTO(String endereco, Double dimensao, String rota) {

    public Pedido mapToPedido() {
        Pedido pedido = new Pedido();
        pedido.setEndereco(this.endereco());
        pedido.setDimensao(this.dimensao());
        pedido.setRota(this.rota());
        return pedido;
    }
}
