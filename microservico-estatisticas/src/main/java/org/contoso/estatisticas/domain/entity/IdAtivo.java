package org.contoso.estatisticas.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdAtivo {
    private Integer id;

    public static IdAtivo of(Integer column) {
        if (column == null) {
            return null;
        }

        return IdAtivo.builder()
                .id(column)
                .build();
    }
}
