package org.contoso.operacoes.calculoinvestimento.domain.exception;

import org.contoso.operacoes.config.exception.BusinessException;
import org.contoso.operacoes.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends BusinessException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
