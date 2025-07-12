package org.contoso.estatisticas.port.output;

import org.contoso.estatisticas.domain.entity.Ativo;

import java.util.List;
import java.util.Set;

public interface AtivoRepositoryPort {

    List<Ativo> findAllByIds(Set<Integer> ids);
}
