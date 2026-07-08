package com.dev_curso.libraryapi.validator;

import com.dev_curso.libraryapi.exceptions.CampoInvalidoException;
import com.dev_curso.libraryapi.exceptions.RegistroDuplicadoException;
import com.dev_curso.libraryapi.model.Livro;
import com.dev_curso.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_PRECO_OBRIGATORIO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro) {
        if (existeIsbnLivro(livro)) {
            throw new RegistroDuplicadoException("Este ISBN já está sendo usado!");
        }
        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidoException("preco", "Livros publicados depois de 2020 devem conter o preço.");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        // Se o preço estiver null E o ano de publicação do livro é 2020 em diante.
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_PRECO_OBRIGATORIO;
    }

    private boolean existeIsbnLivro(Livro livro) {
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());
        // Se for um cadastro:
        if (livro.getId() == null) {
            return livroOptional.isPresent();
        }
        // Se for uma atualização:
        return livroOptional.isPresent() && !livroOptional.get().getIsbn().equals(livro.getIsbn());
    }
}
