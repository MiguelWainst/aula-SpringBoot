package com.dev_curso.libraryapi.validator;

import com.dev_curso.libraryapi.exceptions.RegistroDuplicadoException;
import com.dev_curso.libraryapi.model.Autor;
import com.dev_curso.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private AutorRepository repository;

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Este autor já existe!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorOptional = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        boolean result = autorOptional.isPresent();

        if (autor.getId() == null) {
            return result;
        }

        return result && !autor.getId().equals(autorOptional.get().getId());
    }
}
