package org.contoso.operacoes.calculoinvestimento.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.contoso.operacoes.calculoinvestimento.domain.entity.CompraAtivo;

import java.util.List;

@Data
@Builder
public class PrecoMedioAtivoRequest {
    private final List<CompraAtivo> compras;
}
