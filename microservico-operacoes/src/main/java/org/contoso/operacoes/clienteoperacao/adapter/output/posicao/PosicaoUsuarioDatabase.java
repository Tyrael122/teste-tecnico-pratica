package org.contoso.operacoes.clienteoperacao.adapter.output.posicao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posicao")
public class PosicaoUsuarioDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer usuarioId;
    private Integer ativoId;
    private Integer quantidade;
    private BigDecimal precoMedio;
    private BigDecimal profitLossTotal;
}
