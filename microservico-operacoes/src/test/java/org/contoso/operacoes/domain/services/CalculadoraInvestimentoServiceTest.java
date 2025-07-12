package org.contoso.operacoes.domain.services;

import org.contoso.operacoes.calculoinvestimento.domain.entity.CompraAtivo;
import org.contoso.operacoes.calculoinvestimento.domain.dto.PrecoMedioAtivoRequest;
import org.contoso.operacoes.calculoinvestimento.domain.exception.InvalidRequestException;
import org.contoso.operacoes.calculoinvestimento.domain.services.CalculadoraInvestimentoService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraInvestimentoServiceTest {

    private final CalculadoraInvestimentoService service = new CalculadoraInvestimentoService();

    @Test
    void deveCalcularPrecoMedioCorretamente() {
        var request = PrecoMedioAtivoRequest.builder()
                .compras(List.of(
                        novaCompra(10.00, 5),
                        novaCompra(20.00, 10)
                ))
                .build();

        var response = service.calcularPrecoMedioCompra(request);

        assertEquals(BigDecimal.valueOf(16.67), response.getPrecoMedio());
    }

    @Test
    void deveCalcularCorretamenteQuandoQuantidadeEPrecoForemFracionarios() {
        var request = PrecoMedioAtivoRequest.builder()
                .compras(List.of(
                        novaCompra(3.25, 2.50),
                        novaCompra(1.50, 1.50)
                ))
                .build();

        // totalValor = 3.25*2.5 + 1.5*1.5 = 8.125 + 2.25 = 10.375
        // totalQuantidade = 4.0
        // precoMedio = 10.375 / 4 = 2.59375 → 2.59 (HALF_UP)
        var response = service.calcularPrecoMedioCompra(request);

        assertEquals(BigDecimal.valueOf(2.59), response.getPrecoMedio());
    }

    @Test
    void deveLancarExcecaoParaListaVazia() {
        var request = PrecoMedioAtivoRequest.builder()
                .compras(List.of())
                .build();

        assertThrows(InvalidRequestException.class,
                () -> service.calcularPrecoMedioCompra(request));
    }

    @Test
    void deveLancarExcecaoParaPrecoUnitarioInvalido() {
        var request = PrecoMedioAtivoRequest.builder()
                .compras(List.of(
                        novaCompra(0.00, 10)
                ))
                .build();

        assertThrows(InvalidRequestException.class,
                () -> service.calcularPrecoMedioCompra(request));
    }

    @Test
    void deveLancarExcecaoParaQuantidadeInvalida() {
        var request = PrecoMedioAtivoRequest.builder()
                .compras(List.of(
                        novaCompra(10.00, 0)
                ))
                .build();

        assertThrows(InvalidRequestException.class,
                () -> service.calcularPrecoMedioCompra(request));
    }

    private CompraAtivo novaCompra(double precoUnitario, double quantidade) {
        return CompraAtivo.builder()
                .precoUnitario(BigDecimal.valueOf(precoUnitario))
                .quantidade(BigDecimal.valueOf(quantidade))
                .build();
    }
}
