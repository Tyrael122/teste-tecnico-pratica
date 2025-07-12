package org.contoso.operacoes.clienteoperacao.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.contoso.operacoes.clienteoperacao.domain.entity.TipoOperacao;

@Data
public class NovaOperacaoRequest {

    @NotNull
    private TipoOperacao tipoOperacao; // Ex: COMPRA, VENDA

    @NotEmpty
    private String ticker; // Ex: PETR4

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private int quantidade;
}
