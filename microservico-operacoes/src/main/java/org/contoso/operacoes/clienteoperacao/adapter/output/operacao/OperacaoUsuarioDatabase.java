package org.contoso.operacoes.clienteoperacao.adapter.output.operacao;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operacoes")
public class OperacaoUsuarioDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer usuarioId;
    private Integer ativoId;

    private int quantidade;

    private BigDecimal precoUnitario;
    private Byte tipoOperacao;

    private BigDecimal corretagem;

    private LocalDateTime dataHora = LocalDateTime.now();
}
