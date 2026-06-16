package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    // Salva um autor no bd
    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("RandomGuy");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2003, 7, 13));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    // Atualiza um autor
    @Test
    public void atualizarTest() {
        var id = UUID.fromString("6c1943be-3e1c-4ccd-a881-18ac51cf392e");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setNacionalidade("Israelense");

            repository.save(autorEncontrado);
        }

    }

    // Lista todos os autores
    @Test
    public void listarTest() {
        List<Autor> list = repository.findAll();
        list.forEach(System.out::println);
    }

    // Conta quantos autores tem
    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    // Deleta um autor por id
    @Test
    public void deletePorIdTeste() {
        var id = UUID.fromString("539f1903-770e-4b05-b161-22ad10ea587e");
        repository.deleteById(id);
    }

    // Delata por objeto
    @Test
    public void deletePorObjectTeste() {
        var id = UUID.fromString("539f1903-770e-4b05-b161-22ad10ea587e");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
}
