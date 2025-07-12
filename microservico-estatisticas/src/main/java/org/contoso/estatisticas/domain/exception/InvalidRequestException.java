package org.contoso.estatisticas.domain.exception;

import org.contoso.estatisticas.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends BusinessException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
