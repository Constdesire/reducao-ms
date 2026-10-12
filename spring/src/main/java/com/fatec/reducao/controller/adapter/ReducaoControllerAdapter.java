package com.fatec.reducao.controller.adapter;

import com.fatec.reducao.controller.dto.response.ProcessamentoResponse;
import com.fatec.reducao.entity.Processamento;

public class ReducaoControllerAdapter {
    private ReducaoControllerAdapter() {
    }

    public static ProcessamentoResponse castResponse(Processamento p) {
        return new ProcessamentoResponse(
                p.id(),
                p.candidaturaId(),
                p.vagaId(),
                p.usuario(),
                p.resultado(),
                p.motivo(),
                p.processadoEm()
        );
    }
}
