package org.contoso.operacoes.clienteoperacao.domain.exception;

import org.contoso.operacoes.config.exception.BusinessException;
import org.contoso.operacoes.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.INTERNAL_SERVER_ERROR)
public class OperacaoException extends BusinessException {
    public OperacaoException(String message) {
        super(message);
    }
}
