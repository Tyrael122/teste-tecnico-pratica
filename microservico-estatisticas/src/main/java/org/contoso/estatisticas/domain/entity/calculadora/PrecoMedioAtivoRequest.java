package org.contoso.estatisticas.domain.entity.calculadora;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PrecoMedioAtivoRequest {
    private final List<CompraAtivo> compras;
}
