package org.contoso.operacoes.clienteoperacao.port.output;

import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoResolvedRequest;
import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;

public interface OperacaoUsuarioRepositoryPort {

    OperacaoUsuario criarOperacao(NovaOperacaoResolvedRequest novaOperacaoResolvedRequest);
}
