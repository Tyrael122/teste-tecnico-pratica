package org.contoso.operacoes.calculoinvestimento.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public final class PrecoMedioAtivoResponse {
    private final BigDecimal precoMedio;
}
