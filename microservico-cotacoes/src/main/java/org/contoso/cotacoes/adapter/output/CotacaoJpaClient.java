package org.contoso.cotacoes.adapter.output;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CotacaoJpaClient extends JpaRepository<CotacaoDatabase, Long> {

    @Query(value = """
            SELECT c.* FROM cotacoes c
            WHERE c.ativo_id = (
                SELECT a.id FROM ativos_renda_variavel a WHERE a.codigo = :tickerAtivo
            ) ORDER BY c.data_hora DESC LIMIT 1
            """, nativeQuery = true)
    Optional<CotacaoDatabase> findByTicker(String tickerAtivo);

    Optional<CotacaoDatabase> findByAtivoIdAndPrecoUnitarioAndDataHora(Integer ativoId, BigDecimal precoUnitario, LocalDateTime dataHora);
}
