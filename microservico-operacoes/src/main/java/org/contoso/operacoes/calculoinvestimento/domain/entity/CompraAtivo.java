package org.contoso.operacoes.calculoinvestimento.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CompraAtivo {
    private Integer ativoId;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
}
