package com.teste_aprendizagem.spring_frete.service;

import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.model.entity.Pedido;
import com.teste_aprendizagem.spring_frete.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
