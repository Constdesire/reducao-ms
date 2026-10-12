package com.fatec.reducao.repository;

import com.fatec.reducao.entity.Processamento;
import com.fatec.reducao.repository.adapter.ProcessamentoRepositoryAdapter;
import com.fatec.reducao.repository.jpa.ProcessamentoRepositoryWithJpa;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Implementa a porta do domain usando JPA/H2 (o domain não sabe disso). */
@Repository
public class ProcessamentoRepositoryImpl implements ProcessamentoRepository {

    private final ProcessamentoRepositoryWithJpa jpa;

    public ProcessamentoRepositoryImpl(ProcessamentoRepositoryWithJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Processamento save(Processamento processamento) {
        return ProcessamentoRepositoryAdapter.castOrm(
                jpa.save(ProcessamentoRepositoryAdapter.castEntity(processamento)));
    }

    @Override
    public boolean existsByCandidaturaId(String candidaturaId) {
        return jpa.existsByCandidaturaId(candidaturaId);
    }

    @Override
    public List<Processamento> findTop50ByOrderByIdDesc() {
        return jpa.findTop50ByOrderByIdDesc().stream().map(ProcessamentoRepositoryAdapter::castOrm).toList();
    }
}
