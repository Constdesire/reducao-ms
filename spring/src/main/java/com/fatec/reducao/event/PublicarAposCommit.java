package com.fatec.reducao.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Só envia a mensagem DEPOIS que a transação do banco deu commit: nunca avisamos um resultado
 * que o banco desfez, e quem consome nunca recebe o evento antes do dado estar gravado.
 */
public final class PublicarAposCommit {

    private static final Logger log = LoggerFactory.getLogger(PublicarAposCommit.class);

    private PublicarAposCommit() {
    }

    public static void executar(Runnable envio) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            envio.run();
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                log.info("[COMMIT ] transação confirmada no banco; publicando o resultado no RabbitMQ");
                try {
                    envio.run();
                } catch (RuntimeException ex) {
                    log.error("Falha ao publicar o resultado após o commit", ex);
                }
            }
        });
    }
}
