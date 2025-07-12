package org.contoso.estatisticas.domain.entity.estatisticas.usuario;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.contoso.estatisticas.domain.entity.Ativo;

import java.math.BigDecimal;

@Data
@Builder
@With
public class EstatisticasUsuarioAtivo {
    private Ativo ativo;

    private BigDecimal precoMedio;
    private Integer quantidade;

    private BigDecimal totalCorretagem;

    private BigDecimal totalInvestido;
    private BigDecimal posicao;
    private BigDecimal profitLossTotal;
    private BigDecimal percentualParticipacaoCarteira;
    private BigDecimal cotacaoAtual;
}
