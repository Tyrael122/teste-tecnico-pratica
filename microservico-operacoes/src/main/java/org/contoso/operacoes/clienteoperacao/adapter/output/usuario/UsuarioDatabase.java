package org.contoso.operacoes.clienteoperacao.adapter.output.usuario;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;

    private BigDecimal porcentagemCorretagem;
}
