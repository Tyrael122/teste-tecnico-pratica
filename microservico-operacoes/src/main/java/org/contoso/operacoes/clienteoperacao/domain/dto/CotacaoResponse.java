package org.contoso.operacoes.clienteoperacao.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CotacaoResponse {
    private BigDecimal precoUnitario;
}
