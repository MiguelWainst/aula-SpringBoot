package com.teste_aprendizagem.spring_frete.model.entity;

import com.teste_aprendizagem.spring_frete.model.entity.enums.CargaTipo;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "carga")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Carga {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double preco;

    private Double peso;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CargaTipo cargaTipo;

    @Column(length = 12)
    private Double dimensao;

    @Column
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
