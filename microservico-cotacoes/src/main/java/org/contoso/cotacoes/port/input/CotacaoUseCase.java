package org.contoso.cotacoes.port.input;

import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.dto.CotacaoResponse;

import java.util.List;

public interface CotacaoUseCase {
    CotacaoResponse consultarCotacao(String tickerAtivo);
    void atualizarCotacao(AtualizarCotacaoRequest atualizarCotacaoRequest);

    List<CotacaoResponse> consultarCotacoesLote(List<String> tickersAtivos);
}
