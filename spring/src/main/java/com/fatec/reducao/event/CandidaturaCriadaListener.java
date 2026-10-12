package com.fatec.reducao.event;

import com.fatec.reducao.event.dto.CandidaturaCriadaEvent;
import com.fatec.reducao.service.ReducaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CandidaturaCriadaListener {

    private static final Logger log = LoggerFactory.getLogger(CandidaturaCriadaListener.class);

    private final ReducaoService service;

    public CandidaturaCriadaListener(ReducaoService service) {
        this.service = service;
    }

    // concurrency: vários consumidores ao mesmo tempo -> várias candidaturas chegam juntas na API de vagas
    @RabbitListener(queues = "${event.candidatura-criada.queue}", concurrency = "${reducao.consumidores:4-8}")
    public void receber(CandidaturaCriadaEvent evento) {
        log.info("[RABBIT <<] RECEBIDO candidatura de {} para a vaga #{}", evento.usuario(), evento.vagaId());
        service.processar(evento);
    }
}
