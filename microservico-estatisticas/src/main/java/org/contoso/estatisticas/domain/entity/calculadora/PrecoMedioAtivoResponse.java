package org.contoso.estatisticas.domain.entity.calculadora;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public final class PrecoMedioAtivoResponse {
    private final BigDecimal precoMedio;
}
