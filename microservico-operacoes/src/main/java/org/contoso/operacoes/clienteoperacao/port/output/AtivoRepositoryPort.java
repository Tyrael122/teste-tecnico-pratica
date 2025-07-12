package org.contoso.operacoes.clienteoperacao.port.output;

import org.contoso.operacoes.clienteoperacao.domain.entity.Ativo;

import java.util.Optional;

public interface AtivoRepositoryPort {
    Optional<Ativo> findByTicker(String ticker);
}
