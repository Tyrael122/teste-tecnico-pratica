package org.contoso.cotacoes.port.output;

import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.entity.Cotacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CotacaoDatabasePort {
    Optional<Cotacao> findByTicker(String tickerAtivo);

    Optional<Cotacao> findByComposedPrimaryKey(BigDecimal precoUnitario, String ticker, LocalDateTime timestamp);

    void saveCotacao(AtualizarCotacaoRequest request);
}
