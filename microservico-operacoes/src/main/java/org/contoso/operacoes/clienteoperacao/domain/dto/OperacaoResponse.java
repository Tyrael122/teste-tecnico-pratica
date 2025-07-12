package org.contoso.operacoes.clienteoperacao.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.PosicaoUsuario;

@Data
@Builder
public class OperacaoResponse {
    private OperacaoUsuario operacao;
    private PosicaoUsuario posicaoAtivo;
}
