package com.fatec.reducao.client.dto;

/** Corpo do POST /vagas/{id}/reduzir. */
public record VagasPedido(String candidaturaId, String usuario) {
}
