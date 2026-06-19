package com.teste_aprendizagem.spring_frete.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.model.entity.enums.CargaTipo;

import java.util.UUID;

public record CargaDTO(Double preco, Double peso, @JsonProperty("cargaTipo") String cargaTipo, Double dimensao, UUID pedidoId) {

    public Carga mapToCarga() {
        Carga carga = new Carga();
        carga.setPreco(this.preco);
        carga.setPeso(this.peso);
        carga.setDimensao(this.dimensao);
        if (this.cargaTipo != null) {
            carga.setCargaTipo(CargaTipo.valueOf(this.cargaTipo.toUpperCase().trim()));
        }
        return carga;
    }
}
