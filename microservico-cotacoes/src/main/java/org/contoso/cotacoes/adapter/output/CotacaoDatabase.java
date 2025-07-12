package org.contoso.cotacoes.adapter.output;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cotacoes")
public class CotacaoDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ativoId;
    private BigDecimal precoUnitario;
    private LocalDateTime dataHora;
}
