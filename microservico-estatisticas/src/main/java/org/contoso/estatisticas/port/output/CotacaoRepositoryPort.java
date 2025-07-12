package org.contoso.estatisticas.port.output;

import org.contoso.estatisticas.domain.dto.CotacaoResponse;

import java.util.List;

public interface CotacaoRepositoryPort {
    List<CotacaoResponse> consultarCotacoes(List<String> tickers);
}
