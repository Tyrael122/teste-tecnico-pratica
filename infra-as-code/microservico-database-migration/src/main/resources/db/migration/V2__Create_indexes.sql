CREATE INDEX ultimas_operacoes_usuario_em_ativo ON operacoes (usuario_id, ativo_id, data_hora);
CREATE INDEX cotacao_por_ativo ON cotacoes (ativo_id);
CREATE INDEX posicao_por_ativo ON posicao (ativo_id);