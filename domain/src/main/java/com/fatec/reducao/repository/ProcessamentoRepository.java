package com.fatec.reducao.repository;

import com.fatec.reducao.entity.Processamento;

import java.util.List;

/** Porta de saída do domain: quem implementa (JPA/H2) está no módulo spring. */
public interface ProcessamentoRepository {
    Processamento save(Processamento processamento);

    boolean existsByCandidaturaId(String candidaturaId);

    List<Processamento> findTop50ByOrderByIdDesc();
}
