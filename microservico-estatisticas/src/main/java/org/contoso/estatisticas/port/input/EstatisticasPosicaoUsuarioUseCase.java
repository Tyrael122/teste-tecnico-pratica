package org.contoso.estatisticas.port.input;

import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioGlobais;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuario;
import org.springframework.data.domain.Pageable;

public interface EstatisticasPosicaoUsuarioUseCase {
    EstatisticasUsuario consultarEstatisticasCompletas(Integer usuarioId, Pageable pageable);
    EstatisticasUsuarioGlobais consultarEstatisticasGlobais(Integer usuarioId, Pageable pageable);
}
