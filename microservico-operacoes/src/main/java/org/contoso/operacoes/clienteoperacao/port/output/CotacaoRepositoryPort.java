package org.contoso.operacoes.clienteoperacao.port.output;

import org.contoso.operacoes.clienteoperacao.domain.dto.CotacaoResponse;

import java.util.Optional;

public interface CotacaoRepositoryPort {
    Optional<CotacaoResponse> consultarCotacao(String ticker);
}
