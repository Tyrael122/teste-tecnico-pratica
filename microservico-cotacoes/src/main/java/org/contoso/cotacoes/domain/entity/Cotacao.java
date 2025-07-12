package org.contoso.cotacoes.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Cotacao {
    private Long id;

    private Integer idAtivo;

    private BigDecimal precoUnitario;
    private LocalDateTime timestamp;
}
