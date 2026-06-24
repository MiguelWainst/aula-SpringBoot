package com.dev_curso.libraryapi.controller.common;

import com.dev_curso.libraryapi.controller.dto.ErroCampo;
import com.dev_curso.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.InputStream;
import java.util.List;

/* Classe de exception global */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* Toda vez que o código lançar esse erro, ele cai aqui */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação.",
                listErros
        );
    }
}
