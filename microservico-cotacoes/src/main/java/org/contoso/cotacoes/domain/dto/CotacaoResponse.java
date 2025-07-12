package org.contoso.cotacoes.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;

@Data
@Builder
@With
public class CotacaoResponse {
    private String ticker;
    private BigDecimal precoUnitario;
}
