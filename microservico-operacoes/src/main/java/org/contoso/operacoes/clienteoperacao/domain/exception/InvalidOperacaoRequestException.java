package org.contoso.operacoes.clienteoperacao.domain.exception;

import org.contoso.operacoes.config.exception.BusinessException;
import org.contoso.operacoes.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidOperacaoRequestException extends BusinessException {
    public InvalidOperacaoRequestException(String message) {
        super(message);
    }
}
