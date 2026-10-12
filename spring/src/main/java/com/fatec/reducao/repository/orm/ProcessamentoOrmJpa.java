package com.fatec.reducao.repository.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/** Versão "do banco" do Processamento (tabela PROCESSAMENTO no H2). */
@Getter
@Setter
@Entity
@Table(name = "processamento")
public class ProcessamentoOrmJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String candidaturaId;

    private Long vagaId;

    private String usuario;

    // CONFIRMADA | RECUSADA
    private String resultado;

    private String motivo;

    private Instant processadoEm;
}
