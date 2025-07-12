package org.contoso.estatisticas.domain.entity.estatisticas.usuario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EstatisticasUsuario {
    private EstatisticasUsuarioGlobais estatisticasGlobais;
    private List<EstatisticasUsuarioAtivo> estatisticasAtivos;
}
