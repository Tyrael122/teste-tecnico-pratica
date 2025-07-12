package org.contoso.estatisticas.domain.entity.calculadora;

import lombok.Builder;
import lombok.Data;
import org.contoso.estatisticas.domain.entity.IdAtivo;

import java.math.BigDecimal;

@Data
@Builder
public class CompraAtivo {
    private IdAtivo ativoId;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
}
