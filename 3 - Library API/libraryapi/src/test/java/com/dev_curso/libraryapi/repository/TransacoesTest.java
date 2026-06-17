package com.dev_curso.libraryapi.repository;

import com.dev_curso.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacao;

    /*
    Commit = Confirmar as alterações
    Rollback = Desfazer as alterações
     */
    @Test
    void transacaoSimples() {
        transacao.executar();
    }
}
