package org.contoso.cotacoes.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.dto.CotacaoResponse;
import org.contoso.cotacoes.domain.exception.CotacaoNotFoundException;
import org.contoso.cotacoes.port.input.CotacaoUseCase;
import org.contoso.cotacoes.port.output.CotacaoCachePort;
import org.contoso.cotacoes.port.output.CotacaoDatabasePort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@SuppressWarnings("LoggingSimilarMessage")
public class CotacaoService implements CotacaoUseCase {

    private final static Duration MAX_PRICE_AGE_DURATION = Duration.ofMinutes(2);

    private final CotacaoDatabasePort cotacaoDatabasePort;
    private final CotacaoCachePort cotacaoCachePort;

    private final Map<AtualizarCotacaoRequest, Integer> cotacoesCounter = new HashMap<>();

    public CotacaoService(CotacaoDatabasePort cotacaoDatabasePort, CotacaoCachePort cotacaoCachePort) {
        this.cotacaoDatabasePort = cotacaoDatabasePort;
        this.cotacaoCachePort = cotacaoCachePort;
    }

    @Override
    public List<CotacaoResponse> consultarCotacoesLote(List<String> tickersAtivos) {
        log.info("Consultando cotações para os tickers: {}", tickersAtivos);

        List<CotacaoResponse> cotacoesCache = cotacaoCachePort.consultarCotacao(tickersAtivos);
        log.info("Cotações encontrada no cache: {}", cotacoesCache);

        return cotacoesCache.stream().peek(cotacao -> {
            if (cotacao.getPrecoUnitario() == null) {
                cotacaoDatabasePort.findByTicker(cotacao.getTicker())
                        .ifPresent(cotacaoDatabase -> {
                            if (cotacaoDatabase.getTimestamp().isBefore(LocalDateTime.now().minus(MAX_PRICE_AGE_DURATION))) {
                                log.warn("Cotação do banco de dados está desatualizada: {}", cotacaoDatabase);
                                return;
                            }

                            cotacao.setPrecoUnitario(cotacaoDatabase.getPrecoUnitario());
                            log.info("Atualizando cotação do cache com valor do banco: {}", cotacao);
                        });
            }
        }).toList();
    }

    @Override
    public CotacaoResponse consultarCotacao(String tickerAtivo) {
        return consultarCotacoesLote(List.of(tickerAtivo))
                .stream()
                .findFirst()
                .orElseThrow(() -> CotacaoNotFoundException.of(tickerAtivo));
    }

    @Override
    public void atualizarCotacao(AtualizarCotacaoRequest atualizarCotacaoRequest) {
        atualizarSemErro(atualizarCotacaoRequest);
    }

    private void atualizarSemErro(AtualizarCotacaoRequest request) {
        log.info("Verificando se a cotação já existe: {}", request);

        var existingCotacao = cotacaoDatabasePort.findByComposedPrimaryKey(
                request.getPrecoUnitario(),
                request.getTicker(),
                request.getTimestamp()
        );

        if (existingCotacao.isPresent()) {
            log.info("Cotação já existente: {}", existingCotacao.get());
            return;
        }

        log.info("Cotação não encontrada, salvando nova cotação: {}", request);
        cotacaoDatabasePort.saveCotacao(request);
        cotacaoCachePort.saveCotacao(request, MAX_PRICE_AGE_DURATION);
    }

    private void atualizarComErroParaTestes(AtualizarCotacaoRequest atualizarCotacaoRequest) {
        for (var key : cotacoesCounter.keySet()) {
            if (key.getId().equals(atualizarCotacaoRequest.getId())) {
                cotacoesCounter.put(key, cotacoesCounter.get(key) + 1);

                log.info("Cotação já atualizada {} vezes para {}", cotacoesCounter.get(key), atualizarCotacaoRequest);
                throw new RuntimeException("Exceção simulada para teste de resiliência");
            }
        }

        log.info("Atualizando cotação: {}", atualizarCotacaoRequest);
        cotacoesCounter.put(atualizarCotacaoRequest, 1);

        throw new RuntimeException("Exceção simulada para teste de resiliência");
    }
}
