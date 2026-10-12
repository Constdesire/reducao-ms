package com.fatec.reducao.client.dto;

import java.math.BigDecimal;

/** JSON que a API de vagas devolve. */
public record VagasReducaoResponse(boolean aceita, String motivo, Long vagaId, String titulo, BigDecimal valor,
                                   long inicioEm, int disponiveis) {
}
