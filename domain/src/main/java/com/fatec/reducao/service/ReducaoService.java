package com.fatec.reducao.service;

import com.fatec.reducao.client.VagasClient;
import com.fatec.reducao.entity.Processamento;
import com.fatec.reducao.entity.Reducao;
import com.fatec.reducao.event.ResultadoPublicador;
import com.fatec.reducao.event.dto.CandidaturaCriadaEvent;
import com.fatec.reducao.event.dto.CandidaturaResultadoEvent;
import com.fatec.reducao.repository.ProcessamentoRepository;
import com.fatec.reducao.service.exception.VagaInexistenteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Regras da API de redução. Classe comum (sem @Service), como o LoginService: o módulo spring a cria em
 * config/ReducaoServiceConfig. O @Transactional é só a anotação do spring-tx; quem abre a transação é o Spring.
 */
public class ReducaoService {

    private static final Logger log = LoggerFactory.getLogger(ReducaoService.class);

    private final VagasClient vagasClient;
    private final ProcessamentoRepository repository;
    private final ResultadoPublicador publicador;
    private final long atrasoSimuladoMs;

    public ReducaoService(VagasClient vagasClient, ProcessamentoRepository repository, ResultadoPublicador publicador,
                          long atrasoSimuladoMs) {
        this.vagasClient = vagasClient;
        this.repository = repository;
        this.publicador = publicador;
        this.atrasoSimuladoMs = atrasoSimuladoMs;
    }

    /**
     * 1) consulta/reduz a vaga na API de vagas (é lá que está o lock);
     * 2) grava o histórico no banco deste serviço;
     * 3) pede ao publicador para enviar o resultado ao candidatura-ms (o publicador só envia DEPOIS do commit).
     */
    @Transactional
    public void processar(CandidaturaCriadaEvent e) {
        if (repository.existsByCandidaturaId(e.candidaturaId())) {
            log.warn("[REDUCAO] candidatura {} já foi processada; mensagem repetida ignorada", e.candidaturaId());
            return;
        }
        simularProcessamento();

        log.info("[REDUCAO] consultando a API de vagas para {} (vaga #{})", e.usuario(), e.vagaId());
        CandidaturaResultadoEvent resultado;
        try {
            Reducao r = vagasClient.reduzir(e.vagaId(), e.candidaturaId(), e.usuario());
            resultado = r.aceita()
                    ? new CandidaturaResultadoEvent(e.candidaturaId(), e.vagaId(), e.usuario(), "CONFIRMADA", null,
                            r.titulo(), r.valor(), r.inicioEm())
                    : new CandidaturaResultadoEvent(e.candidaturaId(), e.vagaId(), e.usuario(), "RECUSADA", r.motivo(),
                            r.titulo(), r.valor(), r.inicioEm());
        } catch (VagaInexistenteException ex) {
            resultado = new CandidaturaResultadoEvent(e.candidaturaId(), e.vagaId(), e.usuario(), "RECUSADA",
                    "Esta vaga não existe mais", null, null, null);
        }
        log.info("[REDUCAO] {} -> {}", e.usuario(), resultado.status());

        repository.save(new Processamento(null, e.candidaturaId(), e.vagaId(), e.usuario(), resultado.status(),
                resultado.motivo(), Instant.now()));

        publicador.enviar(resultado);
    }

    @Transactional(readOnly = true)
    public List<Processamento> ultimos() {
        return repository.findTop50ByOrderByIdDesc();
    }

    // só para a demonstração: deixa o status "em análise" visível por alguns instantes no app
    private void simularProcessamento() {
        if (atrasoSimuladoMs > 0) {
            try {
                Thread.sleep(atrasoSimuladoMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
