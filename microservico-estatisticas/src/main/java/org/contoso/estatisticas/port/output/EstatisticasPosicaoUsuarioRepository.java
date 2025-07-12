package org.contoso.estatisticas.port.output;

import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioAtivo;
import org.contoso.estatisticas.domain.entity.estatisticas.usuario.EstatisticasUsuarioGlobais;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface EstatisticasPosicaoUsuarioRepository {
    Optional<EstatisticasUsuarioGlobais> fetchEstatisticasGlobais(Integer idUsuario);
    Map<Integer, EstatisticasUsuarioAtivo> fetchEstatisticasAtivos(Integer idUsuario, Pageable pageable);
}
