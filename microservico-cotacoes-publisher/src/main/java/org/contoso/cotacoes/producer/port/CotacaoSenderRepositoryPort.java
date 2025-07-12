package org.contoso.cotacoes.producer.port;

import org.contoso.cotacoes.producer.domain.AtualizarCotacaoRequest;

public interface CotacaoSenderRepositoryPort {
    void enviarCotacao(AtualizarCotacaoRequest cotacao);
}
