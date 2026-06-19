package com.teste_aprendizagem.spring_frete.controller;

import com.teste_aprendizagem.spring_frete.dto.CargaDTO;
import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.model.entity.Pedido;
import com.teste_aprendizagem.spring_frete.repository.PedidoRepository;
import com.teste_aprendizagem.spring_frete.service.CargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cargas") /* http://localhost:8080/cargas */
public class CargaController {

    CargaService cargaService;

    public CargaController(CargaService cargaService) {
        this.cargaService = cargaService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody CargaDTO carga) {
        Carga cargaEntity = carga.mapToCarga();
        Carga cargaSalva = cargaService.salvarComPedido(cargaEntity, carga.pedidoId());

        /* http://localhost:8080/cargas/{id} */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cargaEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
