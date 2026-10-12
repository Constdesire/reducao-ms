package com.fatec.reducao.client;

import com.fatec.reducao.client.dto.VagasPedido;
import com.fatec.reducao.client.dto.VagasReducaoResponse;
import com.fatec.reducao.entity.Reducao;
import com.fatec.reducao.service.exception.VagaInexistenteException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/** Chamada HTTP de serviço para serviço reducao-ms  API de vagas (vagas-ms). */
@Component
public class VagasClientImpl implements VagasClient {

    private final RestClient restClient;

    public VagasClientImpl(@Value("${vagas.url}") String urlVagas) {
        this.restClient = RestClient.builder().baseUrl(urlVagas).build();
    }

    @Override
    public Reducao reduzir(Long vagaId, String candidaturaId, String usuario) {
        VagasReducaoResponse r = restClient.post()
                .uri("/vagas/{id}/reduzir", vagaId)
                .body(new VagasPedido(candidaturaId, usuario))
                .retrieve()
                .onStatus(status -> status.value() == 404, (req, res) -> {
                    throw new VagaInexistenteException();
                })
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new IllegalStateException("API de vagas respondeu " + res.getStatusCode());
                })
                .body(VagasReducaoResponse.class);
        return new Reducao(r.aceita(), r.motivo(), r.vagaId(), r.titulo(), r.valor(), r.inicioEm(), r.disponiveis());
    }
}
