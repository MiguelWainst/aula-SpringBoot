package com.teste_aprendizagem.spring_frete.repository;

import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.model.entity.enums.CargaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CargaRepository extends JpaRepository<Carga, UUID> {

    List<Carga> findByPedidoId(UUID pedidoId);
    Optional<Carga> findByPrecoAndPesoAndCargaTipoAndDimensao(Double preco, Double peso, CargaTipo cargaTipo, Double dimensao);
}
