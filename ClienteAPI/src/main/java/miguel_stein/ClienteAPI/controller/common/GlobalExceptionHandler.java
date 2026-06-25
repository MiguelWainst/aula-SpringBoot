package miguel_stein.ClienteAPI.controller.common;

import miguel_stein.ClienteAPI.controller.dto.ErroCampo;
import miguel_stein.ClienteAPI.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
}
