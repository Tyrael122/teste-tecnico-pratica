package org.contoso.operacoes.clienteoperacao.adapter.input;

import jakarta.validation.Valid;
import org.contoso.operacoes.clienteoperacao.domain.dto.NovaOperacaoRequest;
import org.contoso.operacoes.clienteoperacao.domain.dto.OperacaoResponse;
import org.contoso.operacoes.clienteoperacao.domain.entity.OperacaoUsuario;
import org.contoso.operacoes.clienteoperacao.port.input.OperacoesUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class OperacoesController {

    private final OperacoesUseCase operacoesUseCase;

    public OperacoesController(OperacoesUseCase operacoesUseCase) {
        this.operacoesUseCase = operacoesUseCase;
    }

    @PostMapping("/{usuarioId}/criar")
    public OperacaoResponse criarOperacao(@PathVariable String usuarioId, @RequestBody @Valid NovaOperacaoRequest request) {
        return operacoesUseCase.criarOperacao(usuarioId, request);
    }
}
