package org.contoso.estatisticas.adapter.output;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCliente;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCorretoraGlobais;
import org.contoso.estatisticas.port.output.EstatisticasCorretoraRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static org.contoso.estatisticas.adapter.output.util.SqlUtil.addSortingToQuery;

@SuppressWarnings("unchecked")
@Repository
public class EstatisticasCorretoraJpaAdapter implements EstatisticasCorretoraRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EstatisticasCorretoraGlobais consultarEstatisticasGlobais() {
        var result = entityManager.createNativeQuery("""
                SELECT SUM(total_investido)  AS total_investido,
                       SUM(posicao)          AS posicao_total,
                       SUM(total_corretagem) AS total_corretagem,
                       (SELECT COUNT(*) FROM operacoes) AS total_operacoes_global
                FROM view_posicao_calculada
                """);

        Object[] row = (Object[]) result.getSingleResult();
        return EstatisticasCorretoraGlobais.builder()
                .valorTotalInvestido((BigDecimal) row[0])
                .posicaoTotal((BigDecimal) row[1])
                .corretagemPagaTotal((BigDecimal) row[2])
                .quantidadeTotalOperacoes(((Long) row[3]))
                .build();
    }

    @Override
    public List<EstatisticasCliente> consultarClientes(Pageable pageable) {
        String baseQuery = """
                SELECT usuario_id,
                       SUM(total_investido)  AS valorTotalInvestido,
                       SUM(posicao)          AS posicaoTotal,
                       SUM(total_corretagem) AS corretagemPagaTotal,
                       SUM(total_operacoes)  AS quantidadeTotalOperacoes
                FROM view_posicao_calculada
                GROUP BY usuario_id
                """;

        String queryWithSort = addSortingToQuery(baseQuery, pageable.getSort(), List.of(
                "idCliente",
                "valorTotalInvestido",
                "posicaoTotal",
                "corretagemPagaTotal",
                "quantidadeTotalOperacoes"
        ));

        var result = entityManager.createNativeQuery(queryWithSort)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<Object[]> rows = result.getResultList();
        return rows.stream().map(row -> EstatisticasCliente.builder()
                        .idCliente((Integer) row[0])
                        .valorTotalInvestido((BigDecimal) row[1])
                        .posicaoTotal((BigDecimal) row[2])
                        .corretagemPagaTotal((BigDecimal) row[3])
                        .quantidadeTotalOperacoes(((BigDecimal) row[4]).longValue())
                        .build())
                .toList();
    }
}
