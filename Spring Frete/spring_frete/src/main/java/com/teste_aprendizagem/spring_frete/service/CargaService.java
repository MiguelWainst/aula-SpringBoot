package com.teste_aprendizagem.spring_frete.service;

import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.model.entity.Pedido;
import com.teste_aprendizagem.spring_frete.repository.CargaRepository;
import com.teste_aprendizagem.spring_frete.repository.PedidoRepository;
import com.teste_aprendizagem.spring_frete.validators.CargaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CargaService {

    private final CargaRepository cargaRepository;
    private final PedidoRepository pedidoRepository;
    private final CargaValidator cargaValidator;

    // Construtor único injeta ambos os repositórios automaticamente
    public CargaService(CargaRepository cargaRepository, PedidoRepository pedidoRepository, CargaValidator cargaValidator) {
        this.cargaRepository = cargaRepository;
        this.pedidoRepository = pedidoRepository;
        this.cargaValidator = cargaValidator;
    }

    public Carga salvar(Carga carga) {
        return  cargaRepository.save(carga);
    }

    @Transactional // Garante que se algo falhar, não salva nada pela metade
    public Carga salvarComPedido(Carga carga, UUID pedidoIdExistente) {
        Pedido pedido;

        if (pedidoIdExistente == null) {
            // Cenário A: É a PRIMEIRA carga. Criamos um Pedido do zero!
            pedido = new Pedido();
            // Podemos inicializar o endereço do pedido com o da primeira carga
            pedido.setEndereco("R Coronel 1473");
            pedido.setPreco(0.0);
            pedido.setPeso(0.0);

            // Salvamos primeiro o pedido porque a tabela CARGA exige um pedido_id NOT NULL
            pedido = pedidoRepository.save(pedido);
        } else {
            // Cenário B: Foi passado um ID de pedido. Buscamos ele no banco!
            pedido = pedidoRepository.findById(pedidoIdExistente)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + pedidoIdExistente));
        }

        // Amarra o pedido encontrado/criado na Carga
        carga.setPedido(pedido);
        cargaValidator.validar(carga);
        Carga cargaSalva = cargaRepository.save(carga);

        // Recalcula os totais do pedido (Soma o preço e peso de todas as cargas vinculadas a ele)
        recalcularTotaisDoPedido(pedido);

        return cargaSalva;
    }

    private void recalcularTotaisDoPedido(Pedido pedido) {
        // Busca todas as cargas atuais que pertencem a este pedido
        // (Isso evita problemas de listas Lazy vazias em memória)
        var cargasDoPedido = cargaRepository.findByPedidoId(pedido.getId());

        double totalPreco = cargasDoPedido.stream().mapToDouble(Carga::getPreco).sum();
        double totalPeso = cargasDoPedido.stream().mapToDouble(Carga::getPeso).sum();

        pedido.setPreco(totalPreco);
        pedido.setPeso(totalPeso);

        pedidoRepository.save(pedido);
    }
}
