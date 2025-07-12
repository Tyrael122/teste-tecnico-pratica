package org.contoso.cotacoes.port.output;

import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.dto.CotacaoResponse;

import java.time.Duration;
import java.util.List;

public interface CotacaoCachePort {
    void saveCotacao(AtualizarCotacaoRequest request, Duration timeToLive);
    List<CotacaoResponse> consultarCotacao(List<String> tickerAtivo);
}
