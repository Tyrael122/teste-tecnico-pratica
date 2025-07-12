package org.contoso.operacoes.clienteoperacao.port.input;

import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoRequest;
import org.contoso.operacoes.clienteoperacao.domain.dto.OperacaoResponse;

public interface OperacoesUseCase {
    OperacaoResponse criarOperacao(String usuarioId, NovaOperacaoRequest request);
}
