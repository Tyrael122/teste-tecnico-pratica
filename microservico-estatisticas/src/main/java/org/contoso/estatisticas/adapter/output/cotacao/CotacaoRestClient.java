package org.contoso.estatisticas.adapter.output.cotacao;

import org.contoso.estatisticas.domain.dto.CotacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "cotacaoRestClient",
        url = "${external.cotacao.service.url}"
)
public interface CotacaoRestClient {

    @PostMapping("/cotacoes/lote")
    List<CotacaoResponse> consultarCotacao(@RequestBody List<String> tickerAtivo);
}
