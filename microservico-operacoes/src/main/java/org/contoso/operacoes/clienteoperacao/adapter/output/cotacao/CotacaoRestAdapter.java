package org.contoso.operacoes.clienteoperacao.adapter.output.cotacao;

import lombok.extern.slf4j.Slf4j;
import org.contoso.operacoes.clienteoperacao.domain.dto.CotacaoResponse;
import org.contoso.operacoes.clienteoperacao.port.output.CotacaoRepositoryPort;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

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
    public Optional<CotacaoResponse> consultarCotacao(String ticker) {
        return circuitBreakerFactory.create("cotacaoCircuitBreaker").run(
                () -> consultarCotacaoFromApi(ticker),
                throwable -> consultarCotacaoFromRedis(throwable, ticker)
        );
    }

    private Optional<CotacaoResponse> consultarCotacaoFromApi(String ticker) {
        log.info("Consultando cotação via REST para o ticker: {}", ticker);
        return cotacaoRestClient.consultarCotacao(ticker);
    }

    private Optional<CotacaoResponse> consultarCotacaoFromRedis(Throwable throwable, String ticker) {
        log.warn("Erro ao consultar cotação via REST para o ticker {}: {}", ticker, throwable.getMessage());
        log.info("Consultando cotação de fallback no Redis para o ticker: {}", ticker);

        String cachedValue = redisTemplate.opsForValue().get(ticker);
        if (cachedValue == null) {
            log.warn("Cotação não encontrada no Redis para o ticker: {}", ticker);
            return Optional.empty();
        }

        log.info("Cotação encontrada no Redis para o ticker {}: {}", ticker, cachedValue);
        return Optional.of(CotacaoResponse.builder()
                .precoUnitario(new BigDecimal(cachedValue))
                .build());
    }
}
