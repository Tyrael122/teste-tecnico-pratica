package org.contoso.estatisticas.adapter.output.ativo;

import org.contoso.estatisticas.domain.entity.Ativo;
import org.contoso.estatisticas.port.output.AtivoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class AtivoJpaAdapter implements AtivoRepositoryPort {

    private final AtivoJpaClient ativoJpaClient;

    public AtivoJpaAdapter(AtivoJpaClient ativoJpaClient) {
        this.ativoJpaClient = ativoJpaClient;
    }

    @Override
    public List<Ativo> findAllByIds(Set<Integer> ids) {
        return ativoJpaClient.findAllById(ids)
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    private Ativo mapToDomain(AtivoDatabase ativoDatabase) {
        return Ativo.builder()
                .id(ativoDatabase.getId())
                .ticker(ativoDatabase.getCodigo())
                .build();
    }
}
