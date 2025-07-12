package org.contoso.estatisticas.adapter.input;

import lombok.extern.slf4j.Slf4j;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCliente;
import org.contoso.estatisticas.domain.entity.estatisticas.corretora.EstatisticasCorretoraGlobais;
import org.contoso.estatisticas.port.input.EstatisticasCorretoraUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/corretora")
public class EstatisticasCorretoraController {

    private final EstatisticasCorretoraUseCase estatisticasCorretoraUseCase;

    public EstatisticasCorretoraController(EstatisticasCorretoraUseCase estatisticasCorretoraUseCase) {
        this.estatisticasCorretoraUseCase = estatisticasCorretoraUseCase;
    }

    @GetMapping("/clientes")
    public List<EstatisticasCliente> consultarClientes(Pageable pageable) {
        log.info("Consultando estatísticas de clientes com paginação: {}", pageable);

        pageable.getSort().stream().iterator()
                .forEachRemaining(order -> log.info("Ordenação: {} {}", order.getProperty(), order.getDirection()));

        return estatisticasCorretoraUseCase.consultarClientes(pageable);
    }

    @GetMapping("/globais")
    public EstatisticasCorretoraGlobais consultarEstatisticasGlobais() {
        return estatisticasCorretoraUseCase.consultarEstatisticasGlobais();
    }
}
