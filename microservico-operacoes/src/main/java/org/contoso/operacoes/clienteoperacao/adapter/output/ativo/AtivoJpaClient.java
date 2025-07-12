package org.contoso.operacoes.clienteoperacao.adapter.output.ativo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtivoJpaClient extends JpaRepository<AtivoDatabase, Long> {
    Optional<AtivoDatabase> findByCodigo(String codigo);
}
