package com.fatec.reducao.controller.dto.response;

import java.time.Instant;

public record ProcessamentoResponse(
        Long id,
        String candidaturaId,
        Long vagaId,
        String usuario,
        String resultado,
        String motivo,
        Instant processadoEm) {
}
