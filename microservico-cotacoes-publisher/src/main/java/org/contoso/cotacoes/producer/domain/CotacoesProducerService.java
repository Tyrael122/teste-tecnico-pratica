package org.contoso.cotacoes.producer.domain;

import jakarta.annotation.PostConstruct;
import org.contoso.cotacoes.producer.port.CotacaoSenderRepositoryPort;
import org.contoso.cotacoes.producer.port.CotacoesProducerUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CotacoesProducerService implements CotacoesProducerUseCase {

    private final CotacaoSenderRepositoryPort cotacaoSenderRepositoryPort;
    private final static List<String> TICKERS = List.of(
            "PETR4",
            "VALE3",
            "ITUB4",
            "BBDC4",
            "ABEV3",
            "BBAS3",
            "WEGE3",
            "CIEL3",
            "RENT3",
            "SUZB3",
            "MGLU3",
            "B3SA3",
            "LREN3",
            "HAPV3",
            "GGBR4",
            "VIVT3",
            "RADL3",
            "NTCO3",
            "AZUL4",
            "CCRO3",
            "UGPA3",
            "ELET3",
            "BRFS3",
            "EMBR3",
            "CSAN3",
            "EQTL3",
            "CYRE3",
            "MRFG3",
            "SBSP3",
            "KLBN11"
    );

    public CotacoesProducerService(CotacaoSenderRepositoryPort cotacaoSenderRepositoryPort) {
        this.cotacaoSenderRepositoryPort = cotacaoSenderRepositoryPort;
    }

    @PostConstruct
    public void init() {
        gerarCotacaoParaTodosTickers();
    }

    @Override
    public void gerarCotacao() {
        gerarCotacao(getRandomTicker());
    }

    public void gerarCotacaoParaTodosTickers() {
        for (String ticker : TICKERS) {
            gerarCotacao(ticker);
        }
    }

    private void gerarCotacao(String ticker) {
        var cotacaoRequest = AtualizarCotacaoRequest.builder()
                .id(UUID.randomUUID())
                .ticker(ticker)
                .precoUnitario(getRandomPrice())
                .timestamp(LocalDateTime.now())
                .build();

        cotacaoSenderRepositoryPort.enviarCotacao(cotacaoRequest);
    }

    private static BigDecimal getRandomPrice() {
        return BigDecimal.valueOf(10 + Math.random() * 90).setScale(2, RoundingMode.HALF_UP);
    }

    private static String getRandomTicker() {
        return TICKERS.get((int) (Math.random() * TICKERS.size()));
    }
}
