package org.contoso.operacoes.clienteoperacao.adapter.output.posicao;

import org.contoso.operacoes.clienteoperacao.domain.entity.PosicaoUsuario;
import org.contoso.operacoes.clienteoperacao.port.output.PosicaoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PosicaoJpaAdapter implements PosicaoRepositoryPort {

    private final PosicaoJpaClient posicaoJpaClient;

    public PosicaoJpaAdapter(PosicaoJpaClient posicaoJpaClient) {
        this.posicaoJpaClient = posicaoJpaClient;
    }

    @Override
    public Optional<PosicaoUsuario> consultarPosicaoUsuario(Integer usuarioId, Integer ativoId) {
        return posicaoJpaClient.findByUsuarioIdAndAtivoId(usuarioId, ativoId)
                .map(this::mapToDomain);
    }

    @Override
    public PosicaoUsuario atualizarPosicaoUsuario(PosicaoUsuario posicaoUsuario) {
        var posicaoSalva = posicaoJpaClient.save(mapToDatabase(posicaoUsuario));
        return mapToDomain(posicaoSalva);
    }

    private PosicaoUsuarioDatabase mapToDatabase(PosicaoUsuario posicaoUsuario) {
        return PosicaoUsuarioDatabase.builder()
                .id(posicaoUsuario.getId())
                .usuarioId(posicaoUsuario.getUsuarioId())
                .ativoId(posicaoUsuario.getAtivoId())
                .quantidade(posicaoUsuario.getQuantidade())
                .precoMedio(posicaoUsuario.getPrecoMedio())
                .profitLossTotal(posicaoUsuario.getProfitLossTotal())
                .build();
    }

    private PosicaoUsuario mapToDomain(PosicaoUsuarioDatabase posicaoDatabase) {
        return PosicaoUsuario.builder()
                .id(posicaoDatabase.getId())
                .usuarioId(posicaoDatabase.getUsuarioId())
                .ativoId(posicaoDatabase.getAtivoId())
                .quantidade(posicaoDatabase.getQuantidade())
                .precoMedio(posicaoDatabase.getPrecoMedio())
                .profitLossTotal(posicaoDatabase.getProfitLossTotal())
                .build();
    }

}
