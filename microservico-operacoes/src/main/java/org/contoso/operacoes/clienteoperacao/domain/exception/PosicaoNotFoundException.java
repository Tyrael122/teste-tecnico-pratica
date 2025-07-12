package org.contoso.operacoes.clienteoperacao.domain.exception;

import org.contoso.operacoes.config.exception.BusinessException;
import org.contoso.operacoes.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class PosicaoNotFoundException extends BusinessException {
    public PosicaoNotFoundException(String message) {
        super(message);
    }
}
