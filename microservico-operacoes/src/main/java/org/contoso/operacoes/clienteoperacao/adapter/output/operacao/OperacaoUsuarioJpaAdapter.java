package org.contoso.operacoes.clienteoperacao.adapter.output.operacao;

import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoResolvedRequest;
import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;
import org.contoso.operacoes.clienteoperacao.domain.entity.TipoOperacao;
import org.contoso.operacoes.clienteoperacao.port.output.OperacaoUsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class OperacaoUsuarioJpaAdapter implements OperacaoUsuarioRepositoryPort {

    private final OperacaoUsuarioJpaClient operacaoUsuarioJpaClient;

    public OperacaoUsuarioJpaAdapter(OperacaoUsuarioJpaClient operacaoUsuarioJpaClient) {
        this.operacaoUsuarioJpaClient = operacaoUsuarioJpaClient;
    }

    @Override
    public OperacaoUsuario criarOperacao(NovaOperacaoResolvedRequest novaOperacaoResolvedRequest) {
        OperacaoUsuarioDatabase operacaoDatabase = mapToDatabase(novaOperacaoResolvedRequest);

        OperacaoUsuarioDatabase savedOperacao = operacaoUsuarioJpaClient.save(operacaoDatabase);

        return mapToEntity(savedOperacao);
    }

    private OperacaoUsuario mapToEntity(OperacaoUsuarioDatabase savedOperacao) {
        return OperacaoUsuario.builder()
                .id(String.valueOf(savedOperacao.getId()))
                .usuarioId(String.valueOf(savedOperacao.getUsuarioId()))
                .ativoId(savedOperacao.getAtivoId())
                .quantidade(savedOperacao.getQuantidade())
                .precoUnitario(savedOperacao.getPrecoUnitario())
                .tipoOperacao(mapTipoOperacao(savedOperacao.getTipoOperacao()))
                .corretagem(savedOperacao.getCorretagem())
                .dataHoraOperacao(savedOperacao.getDataHora())
                .build();
    }

    private OperacaoUsuarioDatabase mapToDatabase(NovaOperacaoResolvedRequest request) {
        OperacaoUsuarioDatabase operacaoDatabase = new OperacaoUsuarioDatabase();
        operacaoDatabase.setUsuarioId(Integer.valueOf(request.getUsuarioId()));
        operacaoDatabase.setAtivoId(request.getAtivoId());

        operacaoDatabase.setQuantidade(request.getQuantidade());
        operacaoDatabase.setPrecoUnitario(request.getPrecoUnitario());

        operacaoDatabase.setTipoOperacao(mapTipoOperacao(request.getTipoOperacao()));
        operacaoDatabase.setCorretagem(request.getCorretagem());

        return operacaoDatabase;
    }

    private Byte mapTipoOperacao(TipoOperacao tipoOperacao) {
        return switch (tipoOperacao) {
            case COMPRA -> 1;
            case VENDA -> 2;
            default -> throw new IllegalArgumentException("Tipo de operação inválido: " + tipoOperacao);
        };
    }

    private TipoOperacao mapTipoOperacao(Byte tipoOperacao) {
        return switch (tipoOperacao) {
            case 1 -> TipoOperacao.COMPRA;
            case 2 -> TipoOperacao.VENDA;
            default -> throw new IllegalArgumentException("Tipo de operação inválido: " + tipoOperacao);
        };
    }
}
