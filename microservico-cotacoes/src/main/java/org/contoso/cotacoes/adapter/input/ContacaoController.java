package org.contoso.cotacoes.adapter.input;

import org.contoso.cotacoes.domain.dto.CotacaoResponse;
import org.contoso.cotacoes.port.input.CotacaoUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ContacaoController {

    private final CotacaoUseCase cotacaoUseCase;

    public ContacaoController(CotacaoUseCase cotacaoUseCase) {
        this.cotacaoUseCase = cotacaoUseCase;
    }

    @GetMapping("/{tickerAtivo}")
    public CotacaoResponse consultarCotacao(@PathVariable String tickerAtivo) {
        return cotacaoUseCase.consultarCotacao(tickerAtivo);
    }

    @PostMapping("/lote")
    public List<CotacaoResponse> consultarCotacoesLote(@RequestBody List<String> tickersAtivos) {
        return cotacaoUseCase.consultarCotacoesLote(tickersAtivos);
    }
}
