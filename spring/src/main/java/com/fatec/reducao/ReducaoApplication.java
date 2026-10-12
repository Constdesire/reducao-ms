package com.fatec.reducao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API de redução de vagas: consome as candidaturas da fila, consulta/reduz a vaga na API de vagas
 * e devolve o resultado por outro evento. É o "vou verificar" do fluxo assíncrono.
 */
@SpringBootApplication
public class ReducaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReducaoApplication.class, args);
    }
}
