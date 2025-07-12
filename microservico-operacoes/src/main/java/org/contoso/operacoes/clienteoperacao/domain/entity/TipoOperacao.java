package org.contoso.operacoes.clienteoperacao.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.contoso.operacoes.clienteoperacao.domain.exception.InvalidOperacaoRequestException;

@Getter
public enum TipoOperacao {
    COMPRA,
    VENDA;

    @JsonCreator
    public static TipoOperacao fromString(String value) {
        for (TipoOperacao tipo : TipoOperacao.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }

        throw new InvalidOperacaoRequestException("Tipo de operação inválido: " + value);
    }
}
