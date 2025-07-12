package org.contoso.estatisticas.port.input;

import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCliente;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCorretoraGlobais;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstatisticasCorretoraUseCase {
    List<EstatisticasCliente> consultarClientes(Pageable pageable);

    EstatisticasCorretoraGlobais consultarEstatisticasGlobais();
}
