package org.contoso.cotacoes.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AtualizarCotacaoRequest {
    private UUID id;
    private String ticker;
    private BigDecimal precoUnitario;
    private LocalDateTime timestamp;
}
