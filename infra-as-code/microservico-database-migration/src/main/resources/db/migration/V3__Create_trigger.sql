DELIMITER //
CREATE TRIGGER IF NOT EXISTS apos_cotacao_atualizar
    AFTER
        INSERT
    ON cotacoes
    FOR EACH ROW
BEGIN
    UPDATE posicao
    SET profit_loss_total = (NEW.preco_unitario - posicao.preco_medio) * posicao.quantidade
    WHERE posicao.ativo_id = NEW.ativo_id AND quantidade > 0;
END //
DELIMITER ;