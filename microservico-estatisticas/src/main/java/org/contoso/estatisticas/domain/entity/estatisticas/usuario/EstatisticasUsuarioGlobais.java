package org.contoso.estatisticas.domain.entity.estatisticas.usuario;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstatisticasUsuarioGlobais {
    private BigDecimal totalCorretagemPaga; // operacao
    private BigDecimal totalInvestido; // posicao
    private BigDecimal posicaoTotal; // posicao
    private BigDecimal profitLossTotal; // posicao
}
