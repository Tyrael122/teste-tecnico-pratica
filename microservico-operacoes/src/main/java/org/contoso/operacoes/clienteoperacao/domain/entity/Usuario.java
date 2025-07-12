package org.contoso.operacoes.clienteoperacao.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Usuario {
    private Integer id;
    private String nome;
    private String email;
    private BigDecimal porcentagemCorretagem;
}
