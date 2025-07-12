package org.contoso.operacoes.clienteoperacao.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@With
public class OperacaoUsuario {
    private String id;
    private String usuarioId;
    private Integer ativoId;
    private TipoOperacao tipoOperacao;
    private String ticker;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal corretagem;
    private BigDecimal valorTotalSemEncargos;
    private LocalDateTime dataHoraOperacao;
}
