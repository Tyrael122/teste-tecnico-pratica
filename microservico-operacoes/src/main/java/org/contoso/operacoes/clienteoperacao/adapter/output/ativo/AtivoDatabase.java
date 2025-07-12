package org.contoso.operacoes.clienteoperacao.adapter.output.ativo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ativos_renda_variavel")
public class AtivoDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String nome;
}
