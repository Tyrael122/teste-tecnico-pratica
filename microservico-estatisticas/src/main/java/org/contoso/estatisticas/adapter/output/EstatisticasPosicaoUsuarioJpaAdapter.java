package org.contoso.estatisticas.adapter.output;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioAtivo;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioGlobais;
import org.contoso.estatisticas.port.output.EstatisticasPosicaoUsuarioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlNoDataSourceInspection", "JpaQueryApiInspection", "unchecked"})
@Repository
public class EstatisticasPosicaoUsuarioJpaAdapter implements EstatisticasPosicaoUsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EstatisticasUsuarioGlobais> fetchEstatisticasGlobais(Integer idUsuario) {
        var result = entityManager.createNativeQuery("""
                        SELECT SUM(posicao.total_corretagem)  AS total_corretagem_paga,
                               SUM(posicao.total_investido)   AS total_investido,
                               SUM(posicao.quantidade)        AS quantidade,
                               SUM(posicao.posicao)           AS posicao,
                               SUM(posicao.profit_loss_total) AS profit_loss
                        FROM view_posicao_calculada AS posicao
                        WHERE quantidade > 0 AND posicao.usuario_id = :idUsuario
                        """, Object[].class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();

        if (result.isEmpty()) return Optional.empty();
        if (!(result.get(0) instanceof Object[] row)) {
            throw new IllegalStateException("Query did not return expected result type");
        }

        return Optional.of(EstatisticasUsuarioGlobais.builder()
                .totalCorretagemPaga((BigDecimal) row[0])
                .totalInvestido((BigDecimal) row[1])
                .posicaoTotal((BigDecimal) row[3])
                .profitLossTotal((BigDecimal) row[4])
                .build());
    }

    @Override
    public Map<Integer, EstatisticasUsuarioAtivo> fetchEstatisticasAtivos(Integer idUsuario, Pageable pageable) {
        List<Object[]> results = entityManager.createNativeQuery("""
                        WITH posicao_geral_usuario AS (
                            SELECT SUM(posicao) AS posicao_geral
                            FROM view_posicao_calculada
                            WHERE quantidade > 0 AND usuario_id = :idUsuario
                        )
                        SELECT
                            ativo_id,
                            preco_medio,
                            quantidade,
                            posicao,
                            total_investido,
                            profit_loss_total,
                            posicao / (SELECT posicao_geral FROM posicao_geral_usuario) * 100 AS percentual_participacao_carteira,
                            total_corretagem
                        FROM view_posicao_calculada
                        WHERE quantidade > 0 AND usuario_id = :idUsuario;
                        """, Object[].class)
                .setParameter("idUsuario", idUsuario)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (results.isEmpty()) return Map.of();

        return results.stream()
                .collect(Collectors.toMap(
                        columns -> (Integer) columns[0],
                        columns -> EstatisticasUsuarioAtivo.builder()
                                .precoMedio((BigDecimal) columns[1])
                                .quantidade((Integer) columns[2])
                                .posicao((BigDecimal) columns[3])
                                .totalInvestido((BigDecimal) columns[4])
                                .profitLossTotal((BigDecimal) columns[5])
                                .percentualParticipacaoCarteira((BigDecimal) columns[6])
                                .totalCorretagem((BigDecimal) columns[7])
                                .build()
                ));
    }
}
