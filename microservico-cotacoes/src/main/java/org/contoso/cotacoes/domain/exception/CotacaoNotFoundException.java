package org.contoso.cotacoes.domain.exception;

import org.contoso.cotacoes.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class CotacaoNotFoundException extends BusinessException {
    protected CotacaoNotFoundException(String message) {
        super(message);
    }

    public static CotacaoNotFoundException of(String tickerAtivo) {
        return new CotacaoNotFoundException("Cotação não encontrada para o ativo: " + tickerAtivo);
    }
}
