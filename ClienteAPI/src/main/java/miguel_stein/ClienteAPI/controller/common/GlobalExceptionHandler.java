package miguel_stein.ClienteAPI.controller.common;

import miguel_stein.ClienteAPI.controller.dto.ErroCampo;
import miguel_stein.ClienteAPI.controller.dto.ErroResposta;
import miguel_stein.ClienteAPI.exception.OperacaoNaoPermitida;
import miguel_stein.ClienteAPI.exception.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> errosCampo = fieldErrors
                .stream()
                .map(fieldError -> new ErroCampo(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())
                )
                .toList();
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação.",
                errosCampo
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ErroResposta handlerRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handlerOperacaoNaoPermitidaException(OperacaoNaoPermitida e) {
        return ErroResposta.padrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta errosInternosDiversos(RuntimeException e) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno: Entre em contato com a equipe de suporte!",
                List.of()
        );
    }
}
