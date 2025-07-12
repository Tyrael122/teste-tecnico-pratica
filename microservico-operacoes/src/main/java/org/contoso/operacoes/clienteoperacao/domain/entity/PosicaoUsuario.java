package org.contoso.operacoes.clienteoperacao.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;

@Data
@Builder
@With
public class PosicaoUsuario {
    private Long id;
    private Integer usuarioId;
    private Integer ativoId;
    private Integer quantidade;
    private BigDecimal precoMedio;
    private BigDecimal profitLossTotal;
}
