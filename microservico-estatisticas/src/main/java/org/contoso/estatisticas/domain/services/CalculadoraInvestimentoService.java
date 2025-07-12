package org.contoso.estatisticas.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.contoso.estatisticas.domain.entity.calculadora.CompraAtivo;
import org.contoso.estatisticas.domain.entity.calculadora.PrecoMedioAtivoRequest;
import org.contoso.estatisticas.domain.entity.calculadora.PrecoMedioAtivoResponse;
import org.contoso.estatisticas.domain.exception.InvalidRequestException;
import org.contoso.estatisticas.port.input.CalculadoraInvestimentoUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
public class CalculadoraInvestimentoService implements CalculadoraInvestimentoUseCase {

    private final static int INNER_SCALE = 4;
    private final static int OUTPUT_SCALE = 2;
    private final static RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    @Override
    public PrecoMedioAtivoResponse calcularPrecoMedioCompra(PrecoMedioAtivoRequest request) {
        var compras = request.getCompras();

        validarRequest(compras);

        BigDecimal precoMedio = calcularPrecoMedio(compras);
        return PrecoMedioAtivoResponse.builder()
                .precoMedio(precoMedio)
                .build();
    }

    private static void validarRequest(List<CompraAtivo> compras) {
        if (compras == null || compras.isEmpty()) {
            throw new InvalidRequestException("A lista de compras não pode ser nula ou vazia.");
        }

        for (var compra : compras) {
            if (compra.getPrecoUnitario() == null || compra.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidRequestException("O preço unitário da compra " + compra + " deve ser maior que zero.");
            }
            if (compra.getQuantidade() == null || compra.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InvalidRequestException("A quantidade da compra " + compra + " deve ser maior que zero.");
            }
        }
    }

    private static BigDecimal calcularPrecoMedio(List<CompraAtivo> compras) {
        BigDecimal totalValor = BigDecimal.ZERO.setScale(INNER_SCALE, RoundingMode.HALF_UP);
        BigDecimal totalQuantidade = BigDecimal.ZERO.setScale(INNER_SCALE, RoundingMode.HALF_UP);

        for (var compra : compras) {
            BigDecimal valorCompra = compra.getPrecoUnitario().multiply(compra.getQuantidade());

            totalValor = totalValor.add(valorCompra);
            totalQuantidade = totalQuantidade.add(compra.getQuantidade());
        }

        return totalValor.divide(totalQuantidade, DEFAULT_ROUNDING_MODE)
                .setScale(OUTPUT_SCALE, DEFAULT_ROUNDING_MODE);
    }
}
