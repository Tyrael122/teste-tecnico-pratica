package org.contoso.operacoes.clienteoperacao.domain.services;

import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.PosicaoUsuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.TipoOperacao;
import org.contoso.operacoes.clienteoperacao.domain.exception.InvalidOperacaoRequestException;
import org.contoso.operacoes.clienteoperacao.port.output.PosicaoRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PosicaoService {

    private final PosicaoRepositoryPort posicaoRepositoryPort;

    public PosicaoService(PosicaoRepositoryPort posicaoRepositoryPort) {
        this.posicaoRepositoryPort = posicaoRepositoryPort;
    }

    @Transactional
    public PosicaoUsuario atualizarPosicaoUsuario(OperacaoUsuario novaOperacao) {
        int usuarioId = Integer.parseInt(novaOperacao.getUsuarioId());
        int ativoId = novaOperacao.getAtivoId();

        PosicaoUsuario posicaoAtual = consultarPosicaoUsuario(usuarioId, ativoId)
                .orElse(createPosicaoVazia(usuarioId, ativoId));

        BigDecimal novoPrecoMedio = calcularPrecoMedio(posicaoAtual, novaOperacao);

        int novaQuantidade = calcularNovaQuantidade(novaOperacao, posicaoAtual);
        if (novaQuantidade == 0) {
            novoPrecoMedio = BigDecimal.ZERO;
        }

        BigDecimal novoProfitLoss = novaOperacao.getPrecoUnitario()
                .subtract(novoPrecoMedio)
                .multiply(BigDecimal.valueOf(novaQuantidade));

        var novaPosicao = posicaoAtual
                .withQuantidade(novaQuantidade)
                .withPrecoMedio(novoPrecoMedio)
                .withProfitLossTotal(novoProfitLoss);

        return posicaoRepositoryPort.atualizarPosicaoUsuario(novaPosicao);
    }

    public Optional<PosicaoUsuario> consultarPosicaoUsuario(Integer usuarioId, Integer ativoId) {
        return posicaoRepositoryPort.consultarPosicaoUsuario(usuarioId, ativoId);
    }

    private PosicaoUsuario createPosicaoVazia(Integer usuarioId, Integer ativoId) {
        return PosicaoUsuario.builder()
                .usuarioId(usuarioId)
                .ativoId(ativoId)
                .quantidade(0)
                .precoMedio(BigDecimal.ZERO)
                .profitLossTotal(BigDecimal.ZERO)
                .build();
    }

    private static int calcularNovaQuantidade(OperacaoUsuario novaOperacao, PosicaoUsuario posicaoAtual) {
        int novaQuantidade = 0;
        switch (novaOperacao.getTipoOperacao()) {
            case COMPRA -> novaQuantidade = posicaoAtual.getQuantidade() + novaOperacao.getQuantidade();
            case VENDA -> novaQuantidade = posicaoAtual.getQuantidade() - novaOperacao.getQuantidade();
        }

        if (novaQuantidade < 0) {
            throw new InvalidOperacaoRequestException("Quantidade de ações não pode ser negativa após a operação");
        }
        return novaQuantidade;
    }

    /**
     * Calcula o novo preço médio da posição do usuário após uma nova operação.
     * Se a operação for de venda, o preço médio permanece o mesmo.
     * Se for de compra, calcula o novo preço médio ponderado.
     *
     * @param posicaoAtual A posição atual do usuário.
     * @param novaOperacao A nova operação a ser considerada.
     * @return O novo preço médio calculado.
     */
    private BigDecimal calcularPrecoMedio(PosicaoUsuario posicaoAtual, OperacaoUsuario novaOperacao) {
        if (novaOperacao.getTipoOperacao() == TipoOperacao.VENDA) {
            return posicaoAtual.getPrecoMedio();
        }

        BigDecimal valorTotalAnterior = posicaoAtual.getPrecoMedio().multiply(BigDecimal.valueOf(posicaoAtual.getQuantidade()));
        BigDecimal valorTotalNovaOperacao = novaOperacao.getPrecoUnitario().multiply(BigDecimal.valueOf(novaOperacao.getQuantidade()));

        BigDecimal valorTotalFinal = valorTotalAnterior.add(valorTotalNovaOperacao);
        int novaQuantidade = posicaoAtual.getQuantidade() + novaOperacao.getQuantidade();

        return valorTotalFinal.divide(BigDecimal.valueOf(novaQuantidade), RoundingMode.HALF_UP);
    }
}
