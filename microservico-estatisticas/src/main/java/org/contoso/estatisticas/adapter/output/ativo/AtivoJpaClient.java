package org.contoso.estatisticas.adapter.output.ativo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtivoJpaClient extends JpaRepository<AtivoDatabase, Integer> {
}
