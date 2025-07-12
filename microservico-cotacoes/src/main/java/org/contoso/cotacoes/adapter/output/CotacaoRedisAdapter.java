package org.contoso.cotacoes.adapter.output;

import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.domain.dto.AtualizarCotacaoRequest;
import org.contoso.cotacoes.domain.dto.CotacaoResponse;
import org.contoso.cotacoes.port.output.CotacaoCachePort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CotacaoRedisAdapter implements CotacaoCachePort {

    private final RedisTemplate<String, String> redisTemplate;

    public CotacaoRedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveCotacao(AtualizarCotacaoRequest request, Duration timeToLive) {
        String key = request.getTicker();
        String value = request.getPrecoUnitario().toString();

        redisTemplate.opsForValue().set(key, value, timeToLive);
    }

    @Override
    public List<CotacaoResponse> consultarCotacao(List<String> tickers) {
        log.info("Consultando cotação no Redis para os tickers: {}", tickers);

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
