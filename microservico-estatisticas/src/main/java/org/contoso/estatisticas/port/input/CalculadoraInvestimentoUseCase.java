package org.contoso.estatisticas.port.input;

import org.contoso.estatisticas.domain.entity.calculadora.PrecoMedioAtivoRequest;
import org.contoso.estatisticas.domain.entity.calculadora.PrecoMedioAtivoResponse;

public interface CalculadoraInvestimentoUseCase {
    PrecoMedioAtivoResponse calcularPrecoMedioCompra(PrecoMedioAtivoRequest request);
}
