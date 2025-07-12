package org.contoso.estatisticas.domain.services;

import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCliente;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCorretoraGlobais;
import org.contoso.estatisticas.port.input.EstatisticasCorretoraUseCase;
import org.contoso.estatisticas.port.output.EstatisticasCorretoraRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstatisticasCorretoraService implements EstatisticasCorretoraUseCase {

    private final EstatisticasCorretoraRepositoryPort estatisticasCorretoraRepositoryPort;

    public EstatisticasCorretoraService(EstatisticasCorretoraRepositoryPort estatisticasCorretoraRepositoryPort) {
        this.estatisticasCorretoraRepositoryPort = estatisticasCorretoraRepositoryPort;
    }

    @Override
    public List<EstatisticasCliente> consultarClientes(Pageable pageable) {
//        if (pageable.getSort().isUnsorted()) {
//            pageable = Pageable.ofSize(10).withPage(pageable.getPageNumber());
//        }

        return estatisticasCorretoraRepositoryPort.consultarClientes(pageable);
    }

    @Override
    public EstatisticasCorretoraGlobais consultarEstatisticasGlobais() {
        return estatisticasCorretoraRepositoryPort.consultarEstatisticasGlobais();
    }
}
