package com.fatec.reducao.event;

import com.fatec.reducao.event.dto.CandidaturaResultadoEvent;

/** Porta de saída: o domain pede "publique o resultado"; o RabbitMQ fica no módulo spring. */
public interface ResultadoPublicador {
    void enviar(CandidaturaResultadoEvent evento);
}
