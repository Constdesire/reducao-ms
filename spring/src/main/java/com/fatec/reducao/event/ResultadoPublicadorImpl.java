package com.fatec.reducao.event;

import com.fatec.reducao.event.dto.CandidaturaResultadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResultadoPublicadorImpl implements ResultadoPublicador {

    private static final Logger log = LoggerFactory.getLogger(ResultadoPublicadorImpl.class);

    public final String queue;
    public final String exchange;
    public final RabbitTemplate rabbitTemplate;

    public ResultadoPublicadorImpl(
            RabbitTemplate rabbitTemplate,
            @Value("${event.candidatura-resultado.queue}") String queue,
            @Value("${event.candidatura-resultado.exchange}") String exchange) {
        this.queue = queue;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    /** O envio só acontece depois do commit da transação do ReducaoService (PublicarAposCommit). */
    @Override
    public void enviar(CandidaturaResultadoEvent evento) {
        PublicarAposCommit.executar(() -> publicar(evento));
    }

    private void publicar(CandidaturaResultadoEvent evento) {
        try {
            rabbitTemplate.convertAndSend(exchange, queue, evento);
            log.info("[RABBIT >>] PUBLICADO exchange={} fila={} resultado={} usuario={}", exchange, queue,
                    evento.status(), evento.usuario());
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
