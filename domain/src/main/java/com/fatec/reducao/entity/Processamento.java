package com.fatec.reducao.entity;

import java.time.Instant;

/** Histórico de cada candidatura processada (candidaturaId é único: nunca processa a mesma duas vezes). */
public record Processamento(
        Long id,
        String candidaturaId,
        Long vagaId,
        String usuario,
        String resultado,   // CONFIRMADA | RECUSADA
        String motivo,
        Instant processadoEm
) {
}
