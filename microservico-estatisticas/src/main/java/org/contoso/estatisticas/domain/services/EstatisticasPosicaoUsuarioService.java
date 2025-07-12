package org.contoso.estatisticas.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.contoso.estatisticas.domain.dto.CotacaoResponse;
import org.contoso.estatisticas.domain.entity.Ativo;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioAtivo;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioGlobais;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuario;
import org.contoso.estatisticas.port.input.EstatisticasPosicaoUsuarioUseCase;
import org.contoso.estatisticas.port.output.AtivoRepositoryPort;
import org.contoso.estatisticas.port.output.CotacaoRepositoryPort;
import org.contoso.estatisticas.port.output.EstatisticasPosicaoUsuarioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EstatisticasPosicaoUsuarioService implements EstatisticasPosicaoUsuarioUseCase {

    private final EstatisticasPosicaoUsuarioRepository estatisticasPosicaoUsuarioRepository;
    private final AtivoRepositoryPort ativoRepositoryPort;
    private final CotacaoRepositoryPort cotacaoRepositoryPort;

    public EstatisticasPosicaoUsuarioService(EstatisticasPosicaoUsuarioRepository estatisticasPosicaoUsuarioRepository, AtivoRepositoryPort ativoRepositoryPort, CotacaoRepositoryPort cotacaoRepositoryPort) {
        this.estatisticasPosicaoUsuarioRepository = estatisticasPosicaoUsuarioRepository;
        this.ativoRepositoryPort = ativoRepositoryPort;
        this.cotacaoRepositoryPort = cotacaoRepositoryPort;
    }

    @Override
    public EstatisticasUsuario consultarEstatisticasCompletas(Integer usuarioId, Pageable pageable) {
        Optional<EstatisticasUsuarioGlobais> estatisticasGlobais = estatisticasPosicaoUsuarioRepository.fetchEstatisticasGlobais(usuarioId);
        if (estatisticasGlobais.isEmpty()) {
            return EstatisticasUsuario.builder()
                    .estatisticasGlobais(emptyEstatisticaGlobal())
                    .estatisticasAtivos(Collections.emptyList())
                    .build();
        }

        List<EstatisticasUsuarioAtivo> estatisticasUsuarioAtivos = consultarEstatisticasAtivos(usuarioId, pageable);

        return EstatisticasUsuario.builder()
                .estatisticasGlobais(estatisticasGlobais.get())
                .estatisticasAtivos(estatisticasUsuarioAtivos)
                .build();
    }

    @Override
    public EstatisticasUsuarioGlobais consultarEstatisticasGlobais(Integer usuarioId, Pageable pageable) {
        return estatisticasPosicaoUsuarioRepository.fetchEstatisticasGlobais(usuarioId)
                .orElse(emptyEstatisticaGlobal());
    }

    private List<EstatisticasUsuarioAtivo> consultarEstatisticasAtivos(Integer usuarioId, Pageable pageable) {
        Map<Integer, EstatisticasUsuarioAtivo> estatisticasAtivosMap = estatisticasPosicaoUsuarioRepository.fetchEstatisticasAtivos(usuarioId, pageable);
        List<Ativo> ativos = ativoRepositoryPort.findAllByIds(estatisticasAtivosMap.keySet());

        Map<String, CotacaoResponse> cotacoesMap = consultarCotacoes(ativos);

        return ativos.stream()
                .map(ativo -> {
                    var estatistica = estatisticasAtivosMap.get(ativo.getId());
                    var cotacao = cotacoesMap.get(ativo.getTicker());

                    return estatistica
                            .withAtivo(ativo)
                            .withCotacaoAtual(cotacao.getPrecoUnitario());
                })
                .toList();
    }

    private Map<String, CotacaoResponse> consultarCotacoes(List<Ativo> ativos) {
        List<String> tickers = ativos.stream()
                .map(Ativo::getTicker)
                .toList();

        return cotacaoRepositoryPort.consultarCotacoes(tickers)
                .stream().collect(
                        Collectors.toMap(
                                CotacaoResponse::getTicker,
                                cotacao -> cotacao
                        )
                );
    }

    private EstatisticasUsuarioGlobais emptyEstatisticaGlobal() {
        return EstatisticasUsuarioGlobais.builder()
                .totalCorretagemPaga(BigDecimal.ZERO)
                .totalInvestido(BigDecimal.ZERO)
                .posicaoTotal(BigDecimal.ZERO)
                .profitLossTotal(BigDecimal.ZERO)
                .build();
    }
}
