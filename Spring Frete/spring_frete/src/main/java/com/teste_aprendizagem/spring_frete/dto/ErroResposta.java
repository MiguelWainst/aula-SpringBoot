package com.teste_aprendizagem.spring_frete.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String message, List<ErroCampo> erroCampo) {

    public static ErroResposta respostaPadrao(String mensagem) {
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem) {
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
