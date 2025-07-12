package org.contoso.cotacoes.adapter.output;

import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.entity.Cotacao;
import org.contoso.cotacoes.domain.exception.CotacaoNotFoundException;
import org.contoso.cotacoes.port.output.CotacaoDatabasePort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Repository
public class CotacaoJpaAdapter implements CotacaoDatabasePort {

    private final CotacaoJpaClient cotacaoJpaClient;

    public CotacaoJpaAdapter(CotacaoJpaClient cotacaoJpaClient) {
        this.cotacaoJpaClient = cotacaoJpaClient;
    }

    @Override
    public Optional<Cotacao> findByTicker(String tickerAtivo) {
        var cotacaoDatabase = cotacaoJpaClient.findByTicker(tickerAtivo);
        return cotacaoDatabase.map(this::mapToCotacaoResponse);
    }

    @Override
    public Optional<Cotacao> findByComposedPrimaryKey(BigDecimal precoUnitario, String ticker, LocalDateTime timestamp) {
        var ativoId = findByTickerOrElseThrow(ticker)
                .getIdAtivo();

        var cotacaoDatabase = cotacaoJpaClient.findByAtivoIdAndPrecoUnitarioAndDataHora(
                ativoId,
                precoUnitario,
                timestamp.truncatedTo(ChronoUnit.MILLIS)
        );

        return cotacaoDatabase.map(this::mapToCotacaoResponse);
    }

    @Override
    public void saveCotacao(AtualizarCotacaoRequest request) {
        var ativoId = findByTickerOrElseThrow(request.getTicker())
                .getIdAtivo();

        var cotacaoDatabase = mapToCotacaoDatabase(Cotacao.builder()
                .idAtivo(ativoId)
                .precoUnitario(request.getPrecoUnitario())
                .timestamp(request.getTimestamp())
                .build());

        log.info("Salvando cotação no banco de dados: {}", cotacaoDatabase);
        cotacaoJpaClient.save(cotacaoDatabase);
    }

    private Cotacao findByTickerOrElseThrow(String ticker) {
        return findByTicker(ticker)
                .orElseThrow(() -> CotacaoNotFoundException.of(ticker));
    }

    private Cotacao mapToCotacaoResponse(CotacaoDatabase cotacaoDatabase) {
        return Cotacao.builder()
                .id(cotacaoDatabase.getId())
                .idAtivo(cotacaoDatabase.getAtivoId())
                .precoUnitario(cotacaoDatabase.getPrecoUnitario())
                .timestamp(cotacaoDatabase.getDataHora())
                .build();
    }

    private CotacaoDatabase mapToCotacaoDatabase(Cotacao response) {
        CotacaoDatabase cotacaoDatabase = new CotacaoDatabase();
        cotacaoDatabase.setAtivoId(response.getIdAtivo());
        cotacaoDatabase.setPrecoUnitario(response.getPrecoUnitario());
        cotacaoDatabase.setDataHora(response.getTimestamp());
        return cotacaoDatabase;
    }
}
