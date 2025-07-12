package org.contoso.estatisticas.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ativo {
    private Integer id;
    private String ticker;
}
