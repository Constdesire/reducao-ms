package com.fatec.reducao.repository.adapter;

import com.fatec.reducao.entity.Processamento;
import com.fatec.reducao.repository.orm.ProcessamentoOrmJpa;

public class ProcessamentoRepositoryAdapter {

    private ProcessamentoRepositoryAdapter() {
    }

    public static Processamento castOrm(ProcessamentoOrmJpa orm) {
        return new Processamento(
                orm.getId(),
                orm.getCandidaturaId(),
                orm.getVagaId(),
                orm.getUsuario(),
                orm.getResultado(),
                orm.getMotivo(),
                orm.getProcessadoEm()
        );
    }

    public static ProcessamentoOrmJpa castEntity(Processamento entity) {
        ProcessamentoOrmJpa orm = new ProcessamentoOrmJpa();
        orm.setId(entity.id());
        orm.setCandidaturaId(entity.candidaturaId());
        orm.setVagaId(entity.vagaId());
        orm.setUsuario(entity.usuario());
        orm.setResultado(entity.resultado());
        orm.setMotivo(entity.motivo());
        orm.setProcessadoEm(entity.processadoEm());
        return orm;
    }
}
