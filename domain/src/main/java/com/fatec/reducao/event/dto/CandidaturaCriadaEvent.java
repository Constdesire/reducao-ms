package com.fatec.reducao.event.dto;

/** Evento que o candidatura-ms publica quando alguém se candidata. */
public record CandidaturaCriadaEvent(String candidaturaId, Long vagaId, String usuario) {
}
