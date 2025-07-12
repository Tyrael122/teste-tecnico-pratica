package org.contoso.operacoes.clienteoperacao.adapter.output.cotacao;

import org.contoso.operacoes.clienteoperacao.domain.dto.CotacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "cotacaoRestClient",
        url = "${external.cotacao.service.url}"
)
public interface CotacaoRestClient {

    @GetMapping("/cotacoes/{tickerAtivo}")
    Optional<CotacaoResponse> consultarCotacao(@PathVariable("tickerAtivo") String tickerAtivo);
}
