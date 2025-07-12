package org.contoso.operacoes.calculoinvestimento.port.input;

import org.contoso.operacoes.calculoinvestimento.domain.dto.PrecoMedioAtivoRequest;
import org.contoso.operacoes.calculoinvestimento.domain.entity.PrecoMedioAtivoResponse;

public interface CalculadoraInvestimentoUseCase {
    PrecoMedioAtivoResponse calcularPrecoMedioCompra(PrecoMedioAtivoRequest request);
}
