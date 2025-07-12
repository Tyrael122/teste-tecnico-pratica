package org.contoso.cotacoes.producer.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AtualizarCotacaoRequest {
    private UUID id;
    private String ticker;
    private BigDecimal precoUnitario;
    private LocalDateTime timestamp;
}