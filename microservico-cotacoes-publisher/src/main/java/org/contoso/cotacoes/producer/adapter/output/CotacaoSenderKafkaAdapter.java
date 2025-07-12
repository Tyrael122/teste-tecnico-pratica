package org.contoso.cotacoes.producer.adapter.output;

import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.producer.domain.AtualizarCotacaoRequest;
import org.contoso.cotacoes.producer.port.CotacaoSenderRepositoryPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CotacaoSenderKafkaAdapter implements CotacaoSenderRepositoryPort {

    private final static String TOPIC = "cotacoes-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CotacaoSenderKafkaAdapter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarCotacao(AtualizarCotacaoRequest cotacao) {
        log.info("Sending message to topic {}: {}", TOPIC, cotacao);

        kafkaTemplate.send(TOPIC, cotacao);
    }
}
