package org.contoso.operacoes.clienteoperacao.adapter.output.ativo;

import org.contoso.operacoes.clienteoperacao.domain.entity.Ativo;
import org.contoso.operacoes.clienteoperacao.port.output.AtivoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AtivoJpaAdapter implements AtivoRepositoryPort {

    private final AtivoJpaClient ativoJpaClient;

    public AtivoJpaAdapter(AtivoJpaClient ativoJpaClient) {
        this.ativoJpaClient = ativoJpaClient;
    }

    @Override
    public Optional<Ativo> findByTicker(String ticker) {
        return ativoJpaClient.findByCodigo(ticker)
                .map(ativoDatabase -> Ativo.builder()
                        .id(ativoDatabase.getId())
                        .ticker(ativoDatabase.getCodigo())
                        .build());
    }
}
