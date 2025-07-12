package org.contoso.estatisticas.port.output;

import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCliente;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCorretoraGlobais;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstatisticasCorretoraRepositoryPort {
    EstatisticasCorretoraGlobais consultarEstatisticasGlobais();

    List<EstatisticasCliente> consultarClientes(Pageable pageable);
}
