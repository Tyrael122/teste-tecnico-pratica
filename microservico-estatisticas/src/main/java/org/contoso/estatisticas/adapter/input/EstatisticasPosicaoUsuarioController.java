package org.contoso.estatisticas.adapter.input;

import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioGlobais;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuario;
import org.contoso.estatisticas.port.input.EstatisticasPosicaoUsuarioUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class EstatisticasPosicaoUsuarioController {

    private final EstatisticasPosicaoUsuarioUseCase estatisticasPosicaoUsuarioUseCase;

    public EstatisticasPosicaoUsuarioController(EstatisticasPosicaoUsuarioUseCase estatisticasPosicaoUsuarioUseCase) {
        this.estatisticasPosicaoUsuarioUseCase = estatisticasPosicaoUsuarioUseCase;
    }

    @GetMapping("/{usuarioId}/completo")
    public EstatisticasUsuario consultarEstatisticasCompletas(@PathVariable Integer usuarioId, Pageable pageable) {
        return estatisticasPosicaoUsuarioUseCase.consultarEstatisticasCompletas(usuarioId, pageable);
    }

    @GetMapping("/{usuarioId}/globais")
    public EstatisticasUsuarioGlobais consultarEstatisticasGlobais(@PathVariable Integer usuarioId, Pageable pageable) {
        return estatisticasPosicaoUsuarioUseCase.consultarEstatisticasGlobais(usuarioId, pageable);
    }
}
