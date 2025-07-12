CREATE VIEW view_posicao_calculada AS
SELECT posicao.usuario_id,
       posicao.ativo_id,
       preco_medio,
       posicao.quantidade,
       posicao.quantidade * preco_medio                       AS total_investido,
       profit_loss_total,
       (posicao.quantidade * preco_medio) + profit_loss_total AS posicao,
       o.corretagem                                           AS total_corretagem,
       o.total_operacoes                                      AS total_operacoes
FROM posicao
         LEFT JOIN (SELECT usuario_id,
                           ativo_id,
                           SUM(corretagem) AS corretagem,
                           COUNT(*)        AS total_operacoes
                    FROM operacoes
                    GROUP BY usuario_id, ativo_id) o
                   ON posicao.usuario_id = o.usuario_id
                       AND posicao.ativo_id = o.ativo_id;