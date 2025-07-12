package org.contoso.operacoes.clienteoperacao.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoRequest;
import org.contoso.operacoes.clienteoperacao.domain.dto.OperacaoResponse;
import org.contoso.operacoes.clienteoperacao.domain.entity.Usuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.Ativo;
import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoResolvedRequest;
import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.TipoOperacao;
import org.contoso.operacoes.clienteoperacao.domain.exception.InvalidOperacaoRequestException;
import org.contoso.operacoes.clienteoperacao.domain.exception.OperacaoException;
import org.contoso.operacoes.clienteoperacao.domain.exception.PosicaoNotFoundException;
import org.contoso.operacoes.clienteoperacao.port.input.OperacoesUseCase;
import org.contoso.operacoes.clienteoperacao.port.output.AtivoRepositoryPort;
import org.contoso.operacoes.clienteoperacao.port.output.CotacaoRepositoryPort;
import org.contoso.operacoes.clienteoperacao.port.output.OperacaoUsuarioRepositoryPort;
import org.contoso.operacoes.clienteoperacao.port.output.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class OperacoesService implements OperacoesUseCase {

    private final AtivoRepositoryPort ativoRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final CotacaoRepositoryPort cotacaoRepositoryPort;
    private final OperacaoUsuarioRepositoryPort operacaoUsuarioRepositoryPort;

    private final PosicaoService posicaoService;

    public OperacoesService(AtivoRepositoryPort ativoRepositoryPort,
                            UsuarioRepositoryPort usuarioRepositoryPort,
                            CotacaoRepositoryPort cotacaoRepositoryPort,
                            OperacaoUsuarioRepositoryPort operacaoUsuarioRepositoryPort,
                            PosicaoService posicaoService) {
        this.ativoRepositoryPort = ativoRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.cotacaoRepositoryPort = cotacaoRepositoryPort;
        this.operacaoUsuarioRepositoryPort = operacaoUsuarioRepositoryPort;
        this.posicaoService = posicaoService;
    }

    @Override
    @Transactional
    public OperacaoResponse criarOperacao(String usuarioId, NovaOperacaoRequest request) {
        var ativo = ativoRepositoryPort.findByTicker(request.getTicker())
                .orElseThrow(() -> new InvalidOperacaoRequestException("Ativo não encontrado para o ticker: " + request.getTicker()));

        validarPosicaoPermiteOperacao(usuarioId, ativo.getId(), request);

        var precoCotacao = cotacaoRepositoryPort.consultarCotacao(request.getTicker())
                .orElseThrow(() -> new OperacaoException("Cotação não encontrada para o ticker: " + request.getTicker()));

        var valorTotal = precoCotacao.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(request.getQuantidade()));

        var corretagem = calcularTaxaCorretagem(usuarioId, valorTotal);

        var resolvedRequest = NovaOperacaoResolvedRequest.builder()
                .ativoId(ativo.getId())
                .usuarioId(usuarioId)
                .tipoOperacao(request.getTipoOperacao())
                .quantidade(request.getQuantidade())
                .precoUnitario(precoCotacao.getPrecoUnitario())
                .corretagem(corretagem)
                .build();

        var savedOperation = operacaoUsuarioRepositoryPort.criarOperacao(resolvedRequest);
        var enrichedOperation = enrichWithAdditionalValues(savedOperation, ativo, valorTotal);

        var posicaoAtualizada = posicaoService.atualizarPosicaoUsuario(savedOperation);

        return OperacaoResponse.builder()
                .operacao(enrichedOperation)
                .posicaoAtivo(posicaoAtualizada)
                .build();
    }

    private void validarPosicaoPermiteOperacao(String usuarioId, Integer ativoId, NovaOperacaoRequest request) {
        if (request.getTipoOperacao() != TipoOperacao.VENDA) {
            return;
        }

        var posicaoUsuario = posicaoService.consultarPosicaoUsuario(Integer.valueOf(usuarioId), ativoId)
                .orElseThrow(() -> new InvalidOperacaoRequestException("Posição não encontrada para o usuário: " + usuarioId + " e ativo: " + ativoId));

        if (posicaoUsuario.getQuantidade() < request.getQuantidade()) {
            throw new InvalidOperacaoRequestException("Quantidade insuficiente na posição para realizar a venda.");
        }
    }

    private BigDecimal calcularTaxaCorretagem(String usuarioId, BigDecimal valorTotal) {
        BigDecimal porcentagemCorretagem = usuarioRepositoryPort
                .findById(usuarioId)
                .map(Usuario::getPorcentagemCorretagem)
                .orElseThrow(() -> new InvalidOperacaoRequestException("Usuário não encontrado ou porcentagem de corretagem não definida."));

        return valorTotal.multiply(porcentagemCorretagem).movePointLeft(2);
    }

    private OperacaoUsuario enrichWithAdditionalValues(OperacaoUsuario operacaoUsuario, Ativo ativo, BigDecimal totalValue) {
        return operacaoUsuario.withValorTotalSemEncargos(totalValue)
                .withTicker(ativo.getTicker());
    }
}
