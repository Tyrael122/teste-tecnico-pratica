package org.contoso.estatisticas.domain.entity.estatisticas.corretora;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstatisticasCliente {
    private Integer idCliente;

    private BigDecimal valorTotalInvestido;
    private BigDecimal posicaoTotal;
    private BigDecimal corretagemPagaTotal;
    private Long quantidadeTotalOperacoes;
}
