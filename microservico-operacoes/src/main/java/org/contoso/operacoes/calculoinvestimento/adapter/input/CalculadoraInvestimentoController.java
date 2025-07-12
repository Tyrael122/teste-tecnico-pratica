package org.contoso.operacoes.calculoinvestimento.adapter.input;

import org.contoso.operacoes.calculoinvestimento.domain.dto.PrecoMedioAtivoRequest;
import org.contoso.operacoes.calculoinvestimento.domain.entity.PrecoMedioAtivoResponse;
import org.contoso.operacoes.calculoinvestimento.port.input.CalculadoraInvestimentoUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculos/ativos")
public class CalculadoraInvestimentoController {

    private final CalculadoraInvestimentoUseCase calculadoraInvestimentoUseCase;

    public CalculadoraInvestimentoController(CalculadoraInvestimentoUseCase calculadoraInvestimentoUseCase) {
        this.calculadoraInvestimentoUseCase = calculadoraInvestimentoUseCase;
    }

    @PostMapping("/preco-medio-compra")
    public PrecoMedioAtivoResponse calcularPrecoMedioCompra(@RequestBody PrecoMedioAtivoRequest request) {
        return calculadoraInvestimentoUseCase.calcularPrecoMedioCompra(request);
    }
}
