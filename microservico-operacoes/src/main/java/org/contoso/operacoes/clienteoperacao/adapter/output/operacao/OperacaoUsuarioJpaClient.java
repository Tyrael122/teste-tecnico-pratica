package org.contoso.operacoes.clienteoperacao.adapter.output.operacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoUsuarioJpaClient extends JpaRepository<OperacaoUsuarioDatabase, Long> {

    // Aqui você pode adicionar métodos específicos de consulta, se necessário.
    // Por exemplo, para buscar operações por usuário:
    // List<OperacaoUsuarioDatabase> findByUsuarioId(String usuarioId);
}
