package com.fatec.reducao.config;

import com.fatec.reducao.client.VagasClient;
import com.fatec.reducao.event.ResultadoPublicador;
import com.fatec.reducao.repository.ProcessamentoRepository;
import com.fatec.reducao.service.ReducaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Cria o ReducaoService (classe do domain) como bean do Spring, igual ao LoginServiceConfig do login. */
@Configuration
public class ReducaoServiceConfig {

    @Bean
    public ReducaoService reducaoService(VagasClient vagasClient, ProcessamentoRepository repository,
                                         ResultadoPublicador publicador,
                                         @Value("${reducao.atraso-simulado-ms:0}") long atrasoSimuladoMs) {
        return new ReducaoService(vagasClient, repository, publicador, atrasoSimuladoMs);
    }
}
