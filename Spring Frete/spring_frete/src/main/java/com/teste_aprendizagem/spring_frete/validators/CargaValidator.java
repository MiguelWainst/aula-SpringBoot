package com.teste_aprendizagem.spring_frete.validators;

import com.teste_aprendizagem.spring_frete.exceptions.RegistroDuplicadoException;
import com.teste_aprendizagem.spring_frete.model.entity.Carga;
import com.teste_aprendizagem.spring_frete.repository.CargaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CargaValidator {

    CargaRepository cargaRepository;

    public CargaValidator(CargaRepository cargaRepository) {
        this.cargaRepository = cargaRepository;
    }

    public void validar(Carga carga) {
        if (existeAutorCadastrado(carga)) {
            throw new RegistroDuplicadoException("Esta carga já foi cadastrada!");
        }
    }

    private boolean existeAutorCadastrado(Carga carga) {
        Optional<Carga> cargaOptional = cargaRepository.findByPrecoAndPesoAndCargaTipoAndDimensao(
                carga.getPreco(), carga.getPeso(), carga.getCargaTipo(), carga.getDimensao()
        );
        boolean existeCarga = cargaOptional.isPresent();

        if (carga.getId() == null) {
            return existeCarga;
        }

        return existeCarga && !carga.getId().equals(cargaOptional.get().getId());
    }

}
