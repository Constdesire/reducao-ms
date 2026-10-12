package com.fatec.reducao.entity;

import java.math.BigDecimal;

/** Resposta da API de vagas: aceita = true (vaga alocada) ou false (não havia vaga). */
public record Reducao(boolean aceita, String motivo, Long vagaId, String titulo, BigDecimal valor,
                      long inicioEm, int disponiveis) {
}
