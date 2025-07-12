package org.contoso.operacoes.clienteoperacao.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.contoso.operacoes.clienteoperacao.domain.entity.TipoOperacao;

import java.math.BigDecimal;

@Data
@Builder
public class NovaOperacaoResolvedRequest {

    private String usuarioId;
    private Integer ativoId;

    private BigDecimal precoUnitario;

    private TipoOperacao tipoOperacao;

    private int quantidade;

    private BigDecimal corretagem;
}
