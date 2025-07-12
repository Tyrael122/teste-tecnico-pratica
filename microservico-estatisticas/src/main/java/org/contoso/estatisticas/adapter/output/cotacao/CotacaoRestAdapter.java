package org.contoso.estatisticas.adapter.output.cotacao;

import lombok.extern.slf4j.Slf4j;
import org.contoso.estatisticas.domain.dto.CotacaoResponse;
import org.contoso.estatisticas.port.output.CotacaoRepositoryPort;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@Slf4j
@Repository
public class CotacaoRestAdapter implements CotacaoRepositoryPort {

    private final CotacaoRestClient cotacaoRestClient;
    private final RedisTemplate<String, String> redisTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public CotacaoRestAdapter(CotacaoRestClient cotacaoRestClient,
                              RedisTemplate<String, String> redisTemplate,
                              CircuitBreakerFactory circuitBreakerFactory) {
        this.cotacaoRestClient = cotacaoRestClient;
        this.redisTemplate = redisTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public List<CotacaoResponse> consultarCotacoes(List<String> tickers) {
        return circuitBreakerFactory.create("cotacaoCircuitBreaker").run(
                () -> consultarCotacaoFromApi(tickers),
                throwable -> consultarCotacaoFromRedis(throwable, tickers)
        );
    }

    private List<CotacaoResponse> consultarCotacaoFromApi(List<String> tickers) {
        log.info("Consultando cotação via REST para o ticker: {}", tickers);
        return cotacaoRestClient.consultarCotacao(tickers);
    }

    private List<CotacaoResponse> consultarCotacaoFromRedis(Throwable throwable, List<String> tickers) {
        log.warn("Erro ao consultar cotação via REST para o ticker {}: {}", tickers, throwable.getMessage());
        log.info("Consultando cotação de fallback no Redis para o ticker: {}", tickers);

        List<String> cachedValues = redisTemplate.opsForValue().multiGet(tickers);
        if (cachedValues == null || cachedValues.isEmpty()) {
            log.warn("Nenhuma cotação encontrada no Redis para o ticker: {}", tickers);
            return List.of();
        }

        List<CotacaoResponse> cotacaoResponses = new ArrayList<>();
        for (int i = 0; i < tickers.size(); i++) {
            String ticker = tickers.get(i);
            String value = cachedValues.get(i);

            if (value != null && !value.isEmpty() && !value.equals("null")) {
                log.info("Cotação encontrada no Redis: {} - {}", ticker, value);
                cotacaoResponses.add(mapToCotacaoResponse(ticker, value));
            } else {
                log.warn("Cotação não encontrada no Redis para o ticker: {}", ticker);
                cotacaoResponses.add(mapToCotacaoResponse(ticker));
            }
        }

        return cotacaoResponses;
    }

    private CotacaoResponse mapToCotacaoResponse(String ticker) {
        return CotacaoResponse.builder()
                .ticker(ticker)
                .build();
    }

    private CotacaoResponse mapToCotacaoResponse(String ticker, String value) {
        return mapToCotacaoResponse(ticker)
                .withPrecoUnitario(new BigDecimal(value));
    }
}
