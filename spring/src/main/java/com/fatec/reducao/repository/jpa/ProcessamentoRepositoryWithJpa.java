package com.fatec.reducao.repository.jpa;

import com.fatec.reducao.repository.orm.ProcessamentoOrmJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessamentoRepositoryWithJpa extends JpaRepository<ProcessamentoOrmJpa, Long> {
    boolean existsByCandidaturaId(String candidaturaId);

    List<ProcessamentoOrmJpa> findTop50ByOrderByIdDesc();
}
