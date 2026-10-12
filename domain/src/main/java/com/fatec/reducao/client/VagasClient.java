package com.fatec.reducao.client;

import com.fatec.reducao.entity.Reducao;

/** Porta de saída: chamada de serviço para serviço (reducao-ms -> API de vagas). O HTTP fica no módulo spring. */
public interface VagasClient {

    /** Pede à API de vagas para retirar UMA vaga. Se a vaga não existe, lança VagaInexistenteException. */
    Reducao reduzir(Long vagaId, String candidaturaId, String usuario);
}
