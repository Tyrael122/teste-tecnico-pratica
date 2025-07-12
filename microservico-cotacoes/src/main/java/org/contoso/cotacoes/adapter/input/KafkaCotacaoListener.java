package org.contoso.cotacoes.adapter.input;

import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.port.input.CotacaoUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaCotacaoListener {

    private final CotacaoUseCase cotacaoUseCase;

    public KafkaCotacaoListener(CotacaoUseCase cotacaoUseCase) {
        this.cotacaoUseCase = cotacaoUseCase;
    }

    @RetryableTopic(
            attempts = "4", // 1 initial + 3 retries
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "false",
            include = {Exception.class}
    )
    @KafkaListener(topics = "cotacoes-topic", groupId = "my-group")
    public void listen(AtualizarCotacaoRequest message) {
        cotacaoUseCase.atualizarCotacao(message);
    }
}